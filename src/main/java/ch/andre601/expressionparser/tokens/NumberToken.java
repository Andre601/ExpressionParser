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
 * {@link Token Token instance} representing a number (double).
 */
public class NumberToken extends Token{
    
    private final double value;
    
    public NumberToken(double value){
        super("NUMBER(" + value + ")");
        this.value = value;
    }
    
    /**
     * Returns the double value stored within this instance.
     * 
     * @return The double value stored within this instance.
     */
    public double getValue(){
        return value;
    }
}
