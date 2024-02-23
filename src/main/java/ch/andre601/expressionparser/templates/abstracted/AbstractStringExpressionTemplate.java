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
import ch.andre601.expressionparser.expressions.ToDoubleExpression;
import ch.andre601.expressionparser.templates.ExpressionTemplate;

/**
 * Abstract class implementing {@link ExpressionTemplate}.
 * <br>This class converts the provided String into proper {@link ToBooleanExpression} and {@link ToDoubleExpression}
 * instances.
 */
public abstract class AbstractStringExpressionTemplate implements ExpressionTemplate{
    
    /**
     * {@link ToBooleanExpression} holding the String value as boolean value using {@link Boolean#parseBoolean(String)}.
     *
     * @return ToBooleanExpression holding the String value as boolean value using {@link Boolean#parseBoolean(String)}.
     */
    @Override
    public ToBooleanExpression returnBooleanExpression(){
        return Conversions.toBoolean(returnStringExpression());
    }
    
    /**
     * {@link ToDoubleExpression} holding the String value as double value using a {@link java.text.NumberFormat NumberFormatter}.
     * 
     * @return ToDoubleExpression holding the String value as double value using a {@link java.text.NumberFormat NumberFormatter}.
     */
    @Override
    public ToDoubleExpression returnDoubleExpression(){
        return Conversions.toDouble(returnStringExpression());
    }
}
