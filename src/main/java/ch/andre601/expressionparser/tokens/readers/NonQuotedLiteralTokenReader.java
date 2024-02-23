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
import ch.andre601.expressionparser.tokens.StringToken;
import ch.andre601.expressionparser.tokens.Token;

import java.text.ParsePosition;

/**
 * {@link TokenReader TokenReader instance} that parses the provided Text by iterating through it until it finds a space
 * character or hits the end of the text, to then convert into a {@link StringToken StringToken instance}.
 */
public class NonQuotedLiteralTokenReader extends TokenReader{
    
    public NonQuotedLiteralTokenReader(int priority){
        super(priority);
    }
    
    @Override
    public Token read(String text, ParsePosition position, ParseWarnCollector collector){
        int startIndex = position.getIndex();
        int index = position.getIndex();
        
        while(++index < text.length() && !Character.isWhitespace(text.charAt(index)))
            ;
        
        position.setIndex(index);
        
        return new StringToken(text.substring(startIndex, position.getIndex()));
    }
}
