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

package ch.andre601.expressionparser.templates.abstracted;

import ch.andre601.expressionparser.Conversions;
import ch.andre601.expressionparser.expressions.ToDoubleExpression;
import ch.andre601.expressionparser.expressions.ToStringExpression;
import ch.andre601.expressionparser.templates.ExpressionTemplate;

/**
 * Abstract class implementing {@link ExpressionTemplate}.
 * <br>This class converts the provided boolean value into proper {@link ToStringExpression} and {@link ToDoubleExpression}
 * instances.
 */
public abstract class AbstractBooleanExpressionTemplate implements ExpressionTemplate{
    
    /**
     * {@link ToDoubleExpression} holding a double value with either 1, if boolean value is true, or 0.
     *
     * @return ToDoubleExpression holding a double value with either 1, if boolean value is true, or 0.
     */
    @Override
    public ToDoubleExpression returnDoubleExpression(){
        return Conversions.toDouble(returnBooleanExpression());
    }
    
    /**
     * {@link ToStringExpression} holding the boolean value as String.
     * 
     * @return ToStringExpression holding the boolean value as String.
     */
    @Override
    public ToStringExpression returnStringExpression(){
        return Conversions.toString(returnBooleanExpression());
    }
}
