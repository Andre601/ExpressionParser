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
import ch.andre601.expressionparser.internal.CheckUtil;
import ch.andre601.expressionparser.tokens.Token;

import java.text.ParsePosition;

/**
 * {@link TokenReader TokenReader instance} that parses the provided text by looking for appearances of a provided
 * pattern and in such a case returns the provided {@link Token Token instance}.
 */
public class PatternTokenReader extends TokenReader{
    
    private final Token token;
    private final String pattern;
    private final boolean ignoreCase;
    
    /**
     * Creates a new PatternTokenReader instance with the provided {@link Token Token instance} being used for the provided
     * Pattern String.
     * <br>Calling this constructor creates a case-insensitive PatternTokenReader. Should you require a case-sensitive one,
     * use {@link #PatternTokenReader(Token, String, boolean) PatternTokenReader(Token, String, false)} instead.
     * 
     * @param token
     *        The Token to use for this PatternTokenReader.
     * @param pattern
     *        The pattern to use for this PatternTokenReader.
     *
     * @throws IllegalArgumentException
     *         Should token be null, Pattern be null or Pattern be empty.
     * 
     * @see #PatternTokenReader(Token, String, boolean)
     */
    public PatternTokenReader(Token token, String pattern){
        this(token, pattern, true);
    }
    
    /**
     * Creates a new PatternTokenReader instance with the provided {@link Token Token instance} being used for the provided
     * Pattern String. The Boolean option allows you to toggle, whether the lookup should ignore casing of the text.
     *
     * @param token
     *        The Token to use for this PatternTokenReader.
     * @param pattern
     *        The pattern to use for this PatternTokenReader.
     * @param ignoreCase
     *        Whether the PatternTokenReader should ignore case when looking for the pattern.
     * 
     * @throws IllegalArgumentException
     *         Should token be null, Pattern be null or Pattern be empty.
     */
    public PatternTokenReader(Token token, String pattern, boolean ignoreCase){
        super(pattern == null ? 0 : pattern.length());
        
        CheckUtil.notNull(token, PatternTokenReader.class, "Token");
        CheckUtil.notNullOrEmpty(pattern, PatternTokenReader.class, "Pattern");
        
        this.token = token;
        this.pattern = pattern;
        this.ignoreCase = ignoreCase;
    }
    
    @Override
    public Token read(String text, ParsePosition position, ParseWarnCollector collector){
        if(text.regionMatches(ignoreCase, position.getIndex(), pattern, 0, pattern.length())){
            position.setIndex(position.getIndex() + pattern.length());
            return token;
        }
        
        return null;
    }
}
