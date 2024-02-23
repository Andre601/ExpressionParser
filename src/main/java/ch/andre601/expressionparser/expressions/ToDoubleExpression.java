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
 * Expression interface to turn an expression into a double.
 */
public interface ToDoubleExpression extends Expression{
    
    /**
     * Method used to return a double value.
     * 
     * @return double value.
     */
    double evaluate();
    
    /**
     * Creates a new ToDoubleExpression instance containing the provided double value.
     *
     * @param  value
     *         The double value for the ToDoubleExpression to hold.
     *
     * @return New ToDoubleExpression instance.
     */
    static ToDoubleExpression literal(double value){
        return new ConstantToDoubleExpression(value);
    }
    
    /**
     * Pre-made class that can be used through the {@link #literal(double) literal method} of the ToDoubleExpression
     * interface.
     */
    class ConstantToDoubleExpression implements ToDoubleExpression{
        
        private final double value;
        
        public ConstantToDoubleExpression(double value){
            this.value = value;
        }
        
        @Override
        public double evaluate(){
            return value;
        }
    }
}
