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

package ch.andre601.expressionparser.operator;

import ch.andre601.expressionparser.internal.CheckUtil;
import ch.andre601.expressionparser.templates.ExpressionTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * The ListOperator can be used to handling a collection of {@link ExpressionTemplate ExpressionTemplates}.
 */
public abstract class ListOperator extends Operator{
    
    public ListOperator(int priority){
        super(priority);
    }
    
    /**
     * Static method to create a new ListOperator with its {@link #createTemplate(List) createTemplate} method
     * utilizing the provided Function.
     *
     * @param  priority
     *         The priority this Operator should have.
     * @param  function
     *         The Function that should be used when {@link #createTemplate(List) createTemplate} is called for the
     *         ListOperator.
     * 
     * @return A new ListOperator.
     */
    public static ListOperator of(int priority, Function<Collection<ExpressionTemplate>, ExpressionTemplate> function){
        CheckUtil.notNull(function, ListOperator.class, "Function");
        
        return new ListOperator(priority){
            @Override
            public ExpressionTemplate createTemplate(List<ExpressionTemplate> operands){
                return function.apply(operands);
            }
        };
    }
    
    /**
     * {@inheritDoc}
     * 
     * @param a
     *        First ExpressionTemplate to use.
     * @param b
     *        Second ExpressionTemplate to use.
     *
     * @return ExpressionTemplate instance made from the provided ExpressionTemplates.
     */
    @Override
    public ExpressionTemplate createTemplate(ExpressionTemplate a, ExpressionTemplate b){
        CheckUtil.notNull(a, ListOperator.class, "First ExpressionTemplate");
        CheckUtil.notNull(b, ListOperator.class, "Second ExpressionTemplate");
        
        return createTemplate(Arrays.asList(a, b));
    }
    
    /**
     * Method to create an {@link ExpressionTemplate} from a Collection of ExpressionTemplates.
     * 
     * @param  operands
     *         Collection of ExpressionTemplates to use.
     * 
     * @return ExpressionTemplate instance made from the provided ExpressionTemplates.
     */
    public abstract ExpressionTemplate createTemplate(List<ExpressionTemplate> operands);
}
