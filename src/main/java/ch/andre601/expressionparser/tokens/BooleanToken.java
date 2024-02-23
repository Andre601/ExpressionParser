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
 * {@link Token Token instance} representing and holding a boolean value.
 */
public class BooleanToken extends Token{
    
    /**
     * Static BooleanToken instance holding the boolean value true.
     */
    public static BooleanToken TRUE = new BooleanToken(true);
    /**
     * Static BooleanToken instance holding the boolean value false.
     */
    public static BooleanToken FALSE = new BooleanToken(false);
    
    private final boolean value;
    
    public BooleanToken(boolean value){
        super("BOOLEAN(" + value + ")");
        this.value = value;
    }
    
    /**
     * Returns the boolean value stored within this instance.
     * 
     * @return The boolean value stored within this instance.
     */
    public boolean getValue(){
        return value;
    }
}
