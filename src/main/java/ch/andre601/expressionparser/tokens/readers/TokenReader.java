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

package ch.andre601.expressionparser.tokens.readers;

import ch.andre601.expressionparser.ParseWarnCollector;
import ch.andre601.expressionparser.tokens.Token;

import java.text.ParsePosition;

/**
 * The token reader is used to find specific patterns within a provided text to then turn into a Token, "tokenizing" the
 * text over time.
 */
public abstract class TokenReader{
    
    private final int priority;
    
    public TokenReader(int priority){
        this.priority = priority;
    }
    
    /**
     * Priority of the TokenReader instance.
     * 
     * @return Priority of the TokenReader instance.
     */
    public int getPriority(){
        return priority;
    }
    
    /**
     * Abstract method that is called by the ExpressionparserEngine to turn the provided String into a token.
     * <br>The provided {@link ParsePosition ParsePosition instance} indicates the current position within the String
     * and should be updated accordingly when parsing the String to a Token.
     * 
     * <p>Assuming the DefaultExpressionParserEngine is used, returning {@code null} will result in skipping this TokenReader.
     * 
     * @param  text
     *         The text to parse.
     * @param  position
     *         The current position within the text.
     * @param  collector
     *         Instance of the {@link ParseWarnCollector} used for the parsing.
     * 
     * @return Possibly-null {@link Token Token instance}.
     */
    public abstract Token read(String text, ParsePosition position, ParseWarnCollector collector);
}
