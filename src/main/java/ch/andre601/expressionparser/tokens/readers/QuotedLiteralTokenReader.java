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
 * {@link TokenReader TokenReader instance} that parses the provided text by looking for the appearance of the provided
 * char.
 * <br>Should one be found will everything until the next appearance of this char, or until the end of the text, be
 * included in a new {@link StringToken StringToken instance}.
 */
public class QuotedLiteralTokenReader extends TokenReader{
    
    private final char character;
    
    public QuotedLiteralTokenReader(int priority, char character){
        super(priority);
        
        this.character = character;
    }
    
    @Override
    public Token read(String text, ParsePosition position, ParseWarnCollector collector){
        if(position.getIndex() < text.length() && text.charAt(position.getIndex()) == character){
            int startIndex = position.getIndex();
            
            position.setIndex(position.getIndex() + 1);
            
            while(position.getIndex() < text.length() && character != text.charAt(position.getIndex()))
                position.setIndex(position.getIndex() + 1);
            
            position.setIndex(position.getIndex() + 1);
            
            return new StringToken(text.substring(startIndex + 1, position.getIndex() - 1));
        }
        return null;
    }
}
