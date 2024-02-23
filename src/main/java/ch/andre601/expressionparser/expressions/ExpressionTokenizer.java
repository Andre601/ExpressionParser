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

package ch.andre601.expressionparser.expressions;

import ch.andre601.expressionparser.ParseWarnCollector;
import ch.andre601.expressionparser.internal.CheckUtil;
import ch.andre601.expressionparser.tokens.Token;
import ch.andre601.expressionparser.tokens.readers.TokenReader;
import com.google.common.collect.Ordering;

import java.text.ParsePosition;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * The ExpressionTokenizer is responsible for turning a String into a collection of {@link Token Token instances}
 * using a provided list of {@link TokenReader TokenReader instances}.
 * <br>The order of the TokenReaders in the list is based on their {@link TokenReader#getPriority() priority}.
 */
public class ExpressionTokenizer{
    
    private static final Ordering<TokenReader> TOKEN_READER_ORDERING = Ordering.from(Comparator.comparingInt(TokenReader::getPriority)).reverse();
    
    private final List<TokenReader> tokenReaders;
    
    public ExpressionTokenizer(Iterable<TokenReader> tokenReaders){
        this.tokenReaders = TOKEN_READER_ORDERING.immutableSortedCopy(tokenReaders);
    }
    
    /**
     * Parses the provided String and returns a List of {@link Token Tokens} for it.
     * 
     * @param  text
     *         String to parse.
     * @param  collector
     *         {@link ParseWarnCollector} instance to use.
     * 
     * @return List of Tokens.
     */
    public List<Token> parse(String text, ParseWarnCollector collector){
        CheckUtil.notNull(text, ExpressionTokenizer.class, "Text");
        CheckUtil.notNull(collector, ExpressionTokenizer.class, "Collector");
        
        ParsePosition position = new ParsePosition(0);
        
        List<Token> tokens = new LinkedList<>();
        
        next_token:
        while(true){
            while(position.getIndex() < text.length() && Character.isWhitespace(text.charAt(position.getIndex())))
                position.setIndex(position.getIndex() + 1);
            
            if(position.getIndex() >= text.length())
                break;
            
            for(TokenReader tokenReader : tokenReaders){
                Token token;
                if(null != (token = tokenReader.read(text, position, collector))){
                    tokens.add(token);
                    continue next_token;
                }
            }
            
            collector.appendWarningFormatted(position.getIndex(), "Illegal token '%c'.", text.charAt(position.getIndex()));
            break;
        }
        
        return tokens;
    }
}
