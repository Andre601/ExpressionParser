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

package ch.andre601.expressionparser.templates;

import ch.andre601.expressionparser.expressions.ToBooleanExpression;
import ch.andre601.expressionparser.expressions.ToDoubleExpression;
import ch.andre601.expressionparser.expressions.ToStringExpression;

/**
 * Interface used to implement into classes.
 * <br>It contains methods to return ToXExpressions.
 */
public interface ExpressionTemplate{
    
    /**
     * Returns the {@link ToBooleanExpression ToBooleanExpression instance} this ExpressionTemplate holds.
     * 
     * @return ToBooleanExpression instance.
     */
    ToBooleanExpression returnBooleanExpression();
    
    /**
     * Returns the {@link ToDoubleExpression ToDoubleExpression instance} this ExpressionTemplate holds.
     *
     * @return ToDoubleExpression instance.
     */
    ToDoubleExpression returnDoubleExpression();
    
    /**
     * Returns the {@link ToStringExpression ToStringExpression instance} this ExpressionTemplate holds.
     *
     * @return ToStringExpression instance.
     */
    ToStringExpression returnStringExpression();
}
