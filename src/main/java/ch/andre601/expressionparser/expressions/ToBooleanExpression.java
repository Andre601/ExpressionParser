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

/**
 * Expression Interface to turn an expression into a boolean value.
 */
public interface ToBooleanExpression extends Expression{
    
    /**
     * Method used to return a boolean value.
     * 
     * @return boolean value
     */
    boolean evaluate();
    
    /**
     * Creates a new ToBooleanExpression instance containing the provided boolean value.
     * 
     * @param  value
     *         The boolean value for the ToBooleanExpression to hold.
     * 
     * @return New ToBooleanExpression instance.
     */
    static ToBooleanExpression literal(boolean value){
        return new ConstantToBooleanExpression(value);
    }
    
    /**
     * Pre-made class that can be used through the {@link #literal(boolean) literal method} of the ToBooleanExpression
     * interface.
     */
    class ConstantToBooleanExpression implements ToBooleanExpression{
        
        private final boolean value;
        
        public ConstantToBooleanExpression(boolean value){
            this.value = value;
        }
        
        @Override
        public boolean evaluate(){
            return value;
        }
    }
}
