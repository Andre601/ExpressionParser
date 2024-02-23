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
import ch.andre601.expressionparser.operator.Operator;
import ch.andre601.expressionparser.parsers.*;
import ch.andre601.expressionparser.templates.ExpressionTemplate;
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
     * <br>Calling this method is equal to calling {@link DefaultBuilder#createDefault() new DefaultBuilder().createDefault}
     * followed by {@link DefaultBuilder#build()}.
     * 
     * @return A new DefaultExpressionParserEngine with all values already applied.
     */
    public static DefaultExpressionParserEngine createDefault(){
        return new DefaultBuilder().createDefault().build();
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
     * Class extending {@link ch.andre601.expressionparser.ExpressionParserEngine.Builder} with {@link DefaultExpressionParserEngine}.
     * 
     * <p>This class can be used to more easily create a new instance of the DefaultExpressionParserEngine.
     */
    public static class DefaultBuilder extends ExpressionParserEngine.Builder<DefaultExpressionParserEngine>{
        @Override
        public DefaultExpressionParserEngine build(){
            return new DefaultExpressionParserEngine(this.getTokenReaders(), ImmutableMap.copyOf(this.getOperators()), ImmutableList.copyOf(this.getValueReaders()));
        }
    }
}
