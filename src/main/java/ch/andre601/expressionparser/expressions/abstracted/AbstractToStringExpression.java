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
import ch.andre601.expressionparser.expressions.ToStringExpression;
import ch.andre601.expressionparser.internal.CheckUtil;

import java.util.Collection;

/**
 * Abstract class that implements the {@link ToStringExpression} interface.
 * <br>Its intended purpose is to accept a Collection of {@link Expression} extending objects to convert into a
 * {@link ToStringExpression}.
 *
 * @param <T>
 *        Primitive extending {@link Expression}.
 */
public abstract class AbstractToStringExpression<T extends Expression> implements ToStringExpression{
    
    protected final Collection<T> operands;
    
    protected AbstractToStringExpression(Collection<T> operands){
        CheckUtil.notNullOrEmpty(operands, AbstractToStringExpression.class, "Operands");
        
        this.operands = operands;
    }
}
