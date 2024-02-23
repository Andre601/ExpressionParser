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

import java.util.*;

/**
 * This interface can be used to create multiple Expression parsers, all using the same core stuff.
 * <br>It is not required to use this at all.
 */
public interface ExpressionParserEngine{
    
    /**
     * Converts the provided String into a {@link ExpressionTemplate ExpressionTemplate instance} to use.
     * 
     * @param  text
     *         The text to parse into a ExpressionTemplate.
     * @param  collector
     *         {@link ParseWarnCollector ParseWarnCollector instance} used to collect warnings during the parsing.
     * 
     * @return Possibly-null ExpressionTemplate instance.
     */
    ExpressionTemplate compile(String text, ParseWarnCollector collector);
    
    /**
     * Abstract class that can be used to create a new Builder for a {@link ExpressionParserEngine}.
     * <br>The class offers a {@link #createDefault()} method to populate the Lists and Map with default values
     * (TokenReaders, Operators and ValueReaders).
     * 
     * @param <T>
     *        Object implementing the {@link ExpressionParserEngine} interface.
     */
    abstract class Builder<T extends ExpressionParserEngine>{
        private final List<TokenReader> tokenReaders = new ArrayList<>();
        private final Map<Token, Operator> operators = new HashMap<>();
        private final List<ValueReader> valueReaders = new ArrayList<>();
        
        public Builder(){}
        
        /**
         * Applies default values for this Builder class to use. Namely:
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
         * @return This Builder instance with default values applied. Useful for chaining.
         */
        public Builder<T> createDefault(){
            return this 
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
         * Returns a List of currently set {@link TokenReader TokenReaders}.
         *
         * @return Possibly-empty List of TokenReaders.
         */
        public List<TokenReader> getTokenReaders(){
            return tokenReaders;
        }
        
        /**
         * Returns a Map of currently set {@link Token Tokens} and {@link Operator Operators}.
         *
         * @return Possibly-empty Map of Tokens and Operators.
         */
        public Map<Token, Operator> getOperators(){
            return operators;
        }
        
        /**
         * Returns a List of currently set {@link ValueReader ValueReaders}.
         *
         * @return Possibly-empty List of ValueReaders.
         */
        public List<ValueReader> getValueReaders(){
            return valueReaders;
        }
        
        /**
         * Adds a new {@link TokenReader TokenReader instance} to use by the {@link DefaultExpressionParserEngine}.
         *
         * @param  tokenReader
         *         The TokenReader instance to add.
         *
         * @return This builder instance after adding the TokenReader. Useful for chaining.
         */
        public Builder<T> addTokenReader(TokenReader tokenReader){
            CheckUtil.notNull(tokenReader, this.getClass(), "TokenReader");
            
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
        public Builder<T> addOperator(Token token, Operator operator){
            CheckUtil.notNull(token, this.getClass(), "Token");
            CheckUtil.notNull(operator, this.getClass(), "Operator");
            
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
        public Builder<T> addValueReader(ValueReader valueReader){
            CheckUtil.notNull(valueReader, this.getClass(), "ValueReader");
            
            this.valueReaders.add(valueReader);
            return this;
        }
        
        /**
         * Method to build a new instance of what this Builder is using.
         * 
         * @return New instance of T.
         */
        public abstract T build();
    }
}
