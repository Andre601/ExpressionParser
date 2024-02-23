/*
 * Copyright (C) 2024 Andre601
 *
 * Original Copyright and License (C) 2020 Florian Stober
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ch.andre601.expressionparser;

import ch.andre601.expressionparser.expressions.ExpressionTokenizer;
import ch.andre601.expressionparser.internal.CheckUtil;
import ch.andre601.expressionparser.operator.ListOperator;
import ch.andre601.expressionparser.operator.Operator;
import ch.andre601.expressionparser.parsers.*;
import ch.andre601.expressionparser.templates.ExpressionTemplate;
import ch.andre601.expressionparser.templates.ExpressionTemplates;
import ch.andre601.expressionparser.tokens.BooleanToken;
import ch.andre601.expressionparser.tokens.DefaultTokens;
import ch.andre601.expressionparser.tokens.Token;
import ch.andre601.expressionparser.tokens.readers.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.*;

public class DefaultExpressionParserEngine implements ExpressionParserEngine{
    
    private final ExpressionTokenizer tokenizer;
    private final ExpressionTemplateParser parser;
    
    /**
     * Creates a new instance of this class.
     * <br>The provided {@link TokenReader TokenReader list} will be used to create a new {@link ExpressionTokenizer}
     * while the provided Map of {@link Token Tokens} and {@link Operator Operators} will be used to create a new
     * {@link ExpressionTemplateParser}.
     * 
     * @param tokenReaders
     *        List of TokenReaders to use.
     * @param operators
     *        Immutable Map of Token-Operator pairs to use.
     * @param valueReaders
     *        Immutable List of ValueReaders to use.
     */
    public DefaultExpressionParserEngine(List<TokenReader> tokenReaders, ImmutableMap<Token, Operator> operators, ImmutableList<ValueReader> valueReaders){
        CheckUtil.notNullOrEmpty(tokenReaders, DefaultExpressionParserEngine.class, "TokenReaders");
        CheckUtil.notNullOrEmpty(operators, DefaultExpressionParserEngine.class, "Operators");
        CheckUtil.notNullOrEmpty(valueReaders, DefaultExpressionParserEngine.class, "ValueReaders");
        
        this.tokenizer = new ExpressionTokenizer(tokenReaders);
        this.parser = new ExpressionTemplateParser(operators, valueReaders);
    }
    
    /**
     * Convenience method to create a new instance of this class with default values already applied.
     * <br>Calling this method is equal to calling {@link Builder#createDefault()} followed by {@link Builder#build()}.
     * 
     * @return A new DefaultExpressionParserEngine with all values already applied.
     */
    public static DefaultExpressionParserEngine createDefault(){
        return Builder.createDefault().build();
    }
    
    /**
     * Parses the provided text into a {@link ExpressionTemplate} to use.
     * 
     * @param  text
     *         The text to parse into a ExpressionTemplate.
     * @param  collector
     *         {@link ParseWarnCollector ParseWarnCollector instance} used to collect warnings during the parsing.
     *
     * @return New ExpressionTemplate to use.
     */
    @Override
    public ExpressionTemplate compile(String text, ParseWarnCollector collector){
        CheckUtil.notNull(text, DefaultExpressionParserEngine.class, "Text");
        CheckUtil.notNull(collector, DefaultExpressionParserEngine.class, "Collector");
        
        return parser.parse(tokenizer.parse(text, collector), collector);
    }
    
    /**
     * Builder class to add new TokenReaders, Operators and ValueReaders to use in the {@link DefaultExpressionParserEngine}.
     * 
     * <p>A {@link Builder#createDefault() static method} exists to create a new instance with default values applied.
     * See the Javadoc of this method for further details.
     */
    public static class Builder{
        private final List<TokenReader> tokenReaders = new ArrayList<>();
        private final Map<Token, Operator> operators = new HashMap<>();
        private final List<ValueReader> valueReaders = new ArrayList<>();
        
        public Builder(){}
        
        /**
         * Creates a new instance of this Builder class with the following defaults applied:
         * <ul>
         *     <li>TokenReader instances:
         *     <ul>
         *         <li>{@link PatternTokenReader} with {@link BooleanToken#TRUE} for pattern {@code true}</li>
         *         <li>{@link PatternTokenReader} with {@link BooleanToken#FALSE} for pattern {@code false}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#AND} for patterns {@code and} and {@code &&}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#OR} for patterns {@code or} and {@code ||}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#EQUAL} for patterns {@code =} and {@code ==}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#EQUAL_IGNORE_CASE} for patterns {@code ~} and {@code =~}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#NOT_EQUAL} for pattern {@code !=}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#NOT_EQUAL_IGNORE_CASE} for pattern {@code !~}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#STARTS_WITH} for pattern {@code |-}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#ENDS_WITH} for pattern {@code -|}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#CONTAINS} for pattern {@code <_}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#GREATER_THAN} for pattern {@code >}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#GREATER_OR_EQUAL_THAN} for pattern {@code >=}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#LESS_THAN} for pattern {@code <}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#LESS_OR_EQUAL_THAN} for pattern {@code <=}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#OPENING_PARENTHESIS} for pattern {@code (}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#CLOSING_PARENTHESIS} for pattern {@code )}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#NEGATION} for pattern {@code !}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#CONCAT_STRING} for pattern {@code .}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#ADD} for pattern {@code +}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#SUBTRACT} for pattern {@code -}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#MULTIPLY} for pattern {@code *}</li>
         *         <li>{@link PatternTokenReader} with {@link DefaultTokens#DIVIDE} for pattern {@code /}</li>
         *         <li>{@link QuotedLiteralTokenReader} with chars {@code "} and {@code '}, and priority -10</li>
         *         <li>{@link NumberTokenReader} with priority -50</li>
         *         <li>{@link NonQuotedLiteralTokenReader} with priority -100</li>
         *     </ul></li>
         *     <li>Operators:
         *     <ul>
         *         <li>{@link ListOperator} with priority 100 and {@link ExpressionTemplates#and(Collection)} for {@link DefaultTokens#AND}</li>
         *         <li>{@link ListOperator} with priority 50 and {@link ExpressionTemplates#or(Collection)} for {@link DefaultTokens#OR}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#equal(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#EQUAL}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#equalIgnoreCase(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#EQUAL_IGNORE_CASE}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#notEqual(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#NOT_EQUAL}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#notEqualIgnoreCase(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#NOT_EQUAL_IGNORE_CASE}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#startsWith(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#STARTS_WITH}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#endsWith(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#ENDS_WITH}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#contains(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#CONTAINS}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#greater(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#GREATER_THAN}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#greaterOrEqual(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#GREATER_OR_EQUAL_THAN}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#less(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#LESS_THAN}</li>
         *         <li>{@link Operator} with priority 25 and {@link ExpressionTemplates#lessOrEqual(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#LESS_OR_EQUAL_THAN}</li>
         *         <li>{@link ListOperator} with priority 10 and {@link ExpressionTemplates#concat(Collection)} for {@link DefaultTokens#CONCAT_STRING}</li>
         *         <li>{@link ListOperator} with priority 4 and {@link ExpressionTemplates#sum(Collection)} for {@link DefaultTokens#ADD}</li>
         *         <li>{@link Operator} with priority 3 and {@link ExpressionTemplates#sub(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#SUBTRACT}</li>
         *         <li>{@link ListOperator} with priority 2 and {@link ExpressionTemplates#product(Collection)} for {@link DefaultTokens#MULTIPLY}</li>
         *         <li>{@link Operator} with priority 1 and {@link ExpressionTemplates#div(ExpressionTemplate, ExpressionTemplate)} for {@link DefaultTokens#DIVIDE}</li>
         *     </ul>
         *     </li>
         *     <li>ValueReaders:
         *     <ul>
         *         <li>{@link BooleanConstantReader}</li>
         *         <li>{@link NumberConstantReader}</li>
         *         <li>{@link StringConstantReader}</li>
         *         <li>{@link NegatedExpressionReader} with {@link DefaultTokens#NEGATION}</li>
         *         <li>{@link ParenthesisedExpressionReader} with {@link DefaultTokens#OPENING_PARENTHESIS} and {@link DefaultTokens#CLOSING_PARENTHESIS}</li>
         *         <li>{@link NegatedNumberReader} with {@link DefaultTokens#SUBTRACT}</li>
         *     </ul>
         *     </li>
         * </ul>
         * 
         * @return New Builder instance with TokenReader, Operands and ValueReaders pre-filled.
         */
        public static Builder createDefault(){
            return new Builder()
                // Default token readers
                .addTokenReader(new PatternTokenReader(BooleanToken.TRUE, "true"))
                .addTokenReader(new PatternTokenReader(BooleanToken.FALSE, "false"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.AND, "and"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.AND, "&&"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.OR, "or"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.OR, "||"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.EQUAL, "="))
                .addTokenReader(new PatternTokenReader(DefaultTokens.EQUAL, "=="))
                .addTokenReader(new PatternTokenReader(DefaultTokens.EQUAL_IGNORE_CASE, "~"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.EQUAL_IGNORE_CASE, "=~"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.NOT_EQUAL, "!="))
                .addTokenReader(new PatternTokenReader(DefaultTokens.NOT_EQUAL_IGNORE_CASE, "!~"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.STARTS_WITH, "|-"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.ENDS_WITH, "-|"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.CONTAINS, "<_"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.GREATER_THAN, ">"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.GREATER_OR_EQUAL_THAN, ">="))
                .addTokenReader(new PatternTokenReader(DefaultTokens.LESS_THAN, "<"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.LESS_OR_EQUAL_THAN, "<="))
                .addTokenReader(new PatternTokenReader(DefaultTokens.OPENING_PARENTHESIS, "("))
                .addTokenReader(new PatternTokenReader(DefaultTokens.CLOSING_PARENTHESIS, ")"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.NEGATION, "!"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.CONCAT_STRING, "."))
                .addTokenReader(new PatternTokenReader(DefaultTokens.ADD, "+"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.SUBTRACT, "-"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.MULTIPLY, "*"))
                .addTokenReader(new PatternTokenReader(DefaultTokens.DIVIDE, "/"))
                .addTokenReader(new QuotedLiteralTokenReader(-10, '\"'))
                .addTokenReader(new QuotedLiteralTokenReader(-10, '\''))
                .addTokenReader(new NumberTokenReader(-50))
                .addTokenReader(new NonQuotedLiteralTokenReader(-100))
                // Default Operators
                .addOperator(DefaultTokens.AND, ListOperator.of(100, ExpressionTemplates::and))
                .addOperator(DefaultTokens.OR, ListOperator.of(50, ExpressionTemplates::or))
                .addOperator(DefaultTokens.EQUAL, Operator.of(25, ExpressionTemplates::equal))
                .addOperator(DefaultTokens.EQUAL_IGNORE_CASE, Operator.of(25, ExpressionTemplates::equalIgnoreCase))
                .addOperator(DefaultTokens.NOT_EQUAL, Operator.of(25, ExpressionTemplates::notEqual))
                .addOperator(DefaultTokens.NOT_EQUAL_IGNORE_CASE, Operator.of(25, ExpressionTemplates::notEqualIgnoreCase))
                .addOperator(DefaultTokens.STARTS_WITH, Operator.of(25, ExpressionTemplates::startsWith))
                .addOperator(DefaultTokens.ENDS_WITH, Operator.of(25, ExpressionTemplates::endsWith))
                .addOperator(DefaultTokens.CONTAINS, Operator.of(25, ExpressionTemplates::contains))
                .addOperator(DefaultTokens.GREATER_THAN, Operator.of(25, ExpressionTemplates::greater))
                .addOperator(DefaultTokens.GREATER_OR_EQUAL_THAN, Operator.of(25, ExpressionTemplates::greaterOrEqual))
                .addOperator(DefaultTokens.LESS_THAN, Operator.of(25, ExpressionTemplates::less))
                .addOperator(DefaultTokens.LESS_OR_EQUAL_THAN, Operator.of(25, ExpressionTemplates::lessOrEqual))
                .addOperator(DefaultTokens.CONCAT_STRING, ListOperator.of(10, ExpressionTemplates::concat))
                .addOperator(DefaultTokens.ADD, ListOperator.of(4, ExpressionTemplates::sum))
                .addOperator(DefaultTokens.SUBTRACT, Operator.of(3, ExpressionTemplates::sub))
                .addOperator(DefaultTokens.MULTIPLY, ListOperator.of(2, ExpressionTemplates::product))
                .addOperator(DefaultTokens.DIVIDE, Operator.of(1, ExpressionTemplates::div))
                // Value readers
                .addValueReader(new BooleanConstantReader())
                .addValueReader(new NumberConstantReader())
                .addValueReader(new StringConstantReader())
                .addValueReader(new NegatedExpressionReader(DefaultTokens.NEGATION))
                .addValueReader(new ParenthesisedExpressionReader(DefaultTokens.OPENING_PARENTHESIS, DefaultTokens.CLOSING_PARENTHESIS))
                .addValueReader(new NegatedNumberReader(DefaultTokens.SUBTRACT));
                
        }
        
        /**
         * Adds a new {@link TokenReader TokenReader instance} to use by the {@link DefaultExpressionParserEngine}.
         * 
         * @param  tokenReader
         *         The TokenReader instance to add.
         * 
         * @return This builder instance after adding the TokenReader. Useful for chaining.
         */
        public Builder addTokenReader(TokenReader tokenReader){
            CheckUtil.notNull(tokenReader, Builder.class, "TokenReader");
            
            this.tokenReaders.add(tokenReader);
            return this;
        }
        
        /**
         * Adds a new {@link Token Token instance} as key with a new {@link Operator Operator instance} as value to use
         * by the {@link DefaultExpressionParserEngine}.
         * 
         * @param  token
         *         The Token instance to add as key.
         * @param  operator
         *         The Operator instance to use as value.
         * 
         * @return This builder instance after adding the Token and Operator. Useful for chaining.
         */
        public Builder addOperator(Token token, Operator operator){
            CheckUtil.notNull(token, Builder.class, "Token");
            CheckUtil.notNull(operator, Builder.class, "Operator");
            
            this.operators.put(token, operator);
            return this;
        }
        
        /**
         * Adds a new {@link ValueReader ValueReader instance} to use by the {@link DefaultExpressionParserEngine}.
         *
         * @param  valueReader
         *         The ValueReader instance to add.
         *
         * @return This builder instance after adding the ValueReader. Useful for chaining.
         */
        public Builder addValueReader(ValueReader valueReader){
            CheckUtil.notNull(valueReader, Builder.class, "ValueReader");
            
            this.valueReaders.add(valueReader);
            return this;
        }
        
        /**
         * Creates a new {@link DefaultExpressionParserEngine DefaultExpressionParserEngine instance} with all the added
         * {@link TokenReader TokenReaders}, {@link Operator Operators} and {@link ValueReader ValueReaders} applied.
         * 
         * @return A new, usable {@link DefaultExpressionParserEngine}.
         */
        public DefaultExpressionParserEngine build(){
            CheckUtil.notNullOrEmpty(this.tokenReaders, Builder.class, "TokenReaders");
            CheckUtil.notNullOrEmpty(this.operators, Builder.class, "Operators");
            CheckUtil.notNullOrEmpty(this.valueReaders, Builder.class, "ValueReaders");
            
            return new DefaultExpressionParserEngine(this.tokenReaders, ImmutableMap.copyOf(this.operators), ImmutableList.copyOf(this.valueReaders));
        }
    }
}
