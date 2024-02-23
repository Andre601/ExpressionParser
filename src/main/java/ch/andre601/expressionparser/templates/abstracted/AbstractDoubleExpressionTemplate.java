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
import ch.andre601.expressionparser.expressions.ToBooleanExpression;
import ch.andre601.expressionparser.expressions.ToStringExpression;
import ch.andre601.expressionparser.templates.ExpressionTemplate;

/**
 * Abstract class implementing {@link ExpressionTemplate}.
 * <br>This class converts the provided double value in proper {@link ToStringExpression} and {@link ToBooleanExpression}
 * instances.
 */
public abstract class AbstractDoubleExpressionTemplate implements ExpressionTemplate{
    
    /**
     * {@link ToBooleanExpression} holding the boolean value {@code true} if the double value is anything but 0, else false.
     *
     * @return ToBooleanExpression holding the boolean value {@code true} if the double value is anything but 0, else false.
     */
    @Override
    public ToBooleanExpression returnBooleanExpression(){
        return Conversions.toBoolean(returnDoubleExpression());
    }
    
    /**
     * {@link ToStringExpression} holding the double value as String value.
     * 
     * @return ToStringExpression holding the double value as String value.
     */
    @Override
    public ToStringExpression returnStringExpression(){
        return Conversions.toString(returnDoubleExpression());
    }
}
