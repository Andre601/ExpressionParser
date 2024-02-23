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

import ch.andre601.expressionparser.internal.CheckUtil;

/**
 * Expression interface to turn an expression into a String.
 */
public interface ToStringExpression extends Expression{
    
    /**
     * Method used to return a String value.
     * 
     * @return String value.
     */
    String evaluate();
    
    /**
     * Creates a new ToStringExpression instance containing the provided String value.
     *
     * @param  value
     *         The String value for the ToStringExpression to hold.
     *
     * @return New ToStringExpression instance.
     * 
     * @throws IllegalArgumentException
     *         Should the provided String be null.
     */
    static ToStringExpression literal(String value){
        CheckUtil.notNull(value, ToStringExpression.class, "Value");
        
        return new ConstantToStringExpression(value);
    }
    
    /**
     * Pre-made class that can be used through the {@link #literal(String) literal method} of the ToStringExpression
     * interface.
     */
    class ConstantToStringExpression implements ToStringExpression{
        
        private final String value;
        
        public ConstantToStringExpression(String value){
            this.value = value;
        }
        
        @Override
        public String evaluate(){
            return value;
        }
    }
}
