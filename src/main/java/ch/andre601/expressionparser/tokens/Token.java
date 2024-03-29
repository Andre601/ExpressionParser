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

package ch.andre601.expressionparser.tokens;

/**
 * Class used to create Tokens.
 * <br>Tokens are created by the {@link ch.andre601.expressionparser.expressions.ExpressionTokenizer} which converts
 * a String using {@link ch.andre601.expressionparser.parsers.ValueReader ValueReaders}.
 */
public class Token{
    
    private final String id;
    
    public Token(String id){
        this.id = id;
    }
    
    @Override
    public String toString(){
        return id;
    }
}
