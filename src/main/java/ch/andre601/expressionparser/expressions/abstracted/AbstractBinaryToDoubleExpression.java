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

package ch.andre601.expressionparser.expressions.abstracted;

import ch.andre601.expressionparser.expressions.Expression;
import ch.andre601.expressionparser.expressions.ToDoubleExpression;
import ch.andre601.expressionparser.internal.CheckUtil;

/**
 * Abstract class implementing the {@link ToDoubleExpression} interface.
 * <br>Its intended purpose is to accept two {@link Expression} extending objects to create a {@link ToDoubleExpression} from.
 *
 * @param <T>
 *        Primitive extending {@link Expression}.
 */
public abstract class AbstractBinaryToDoubleExpression<T extends Expression> implements ToDoubleExpression{
    
    protected final T a;
    protected final T b;
    
    protected AbstractBinaryToDoubleExpression(T a, T b){
        CheckUtil.notNull(a, AbstractBinaryToDoubleExpression.class, "First Expression");
        CheckUtil.notNull(b, AbstractBinaryToDoubleExpression.class, "Second Expression");
        
        this.a = a;
        this.b = b;
    }
}
