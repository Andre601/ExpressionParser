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

import java.util.function.BiFunction;

public abstract class Operator{
    
    private final int priority;
    
    public Operator(int priority){
        this.priority = priority;
    }
    
    /**
     * Static method to create a new Operator with its {@link #createTemplate(ExpressionTemplate, ExpressionTemplate) createTemplate}
     * method utilizing the provided BiFunction.
     * 
     * @param  priority
     *         The priority this Operator should have.
     * @param  function
     *         The BiFunction that should be used when {@link #createTemplate(ExpressionTemplate, ExpressionTemplate) createTemplate}
     *         is called for the Operator.
     * 
     * @return A new Operator.
     */
    public static Operator of(int priority, BiFunction<ExpressionTemplate, ExpressionTemplate, ExpressionTemplate> function){
        CheckUtil.notNull(function, ListOperator.class, "Function");
        
        return new Operator(priority){
            @Override
            public ExpressionTemplate createTemplate(ExpressionTemplate a, ExpressionTemplate b){
                return function.apply(a, b);
            }
        };
    }
    
    /**
     * Returns the priority set for this Operator.
     * 
     * @return Priority of this Operator.
     */
    public int getPriority(){
        return priority;
    }
    
    /**
     * Method to create an {@link ExpressionTemplate} from two ExpressionTemplates.
     * 
     * @param a
     *        First ExpressionTemplate to use.
     * @param b
     *        Second ExpressionTemplate to use.
     *
     * @return ExpressionTemplate instance made from the provided ExpressionTemplates.
     */
    public abstract ExpressionTemplate createTemplate(ExpressionTemplate a, ExpressionTemplate b);
}
