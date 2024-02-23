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
import ch.andre601.expressionparser.internal.CheckUtil;

/**
 * An {@link ExpressionTemplate ExpressionTemplate instance} that holds a {@link ToBooleanExpression ToBooleanExpression},
 * {@link ToDoubleExpression ToDoubleExpression} and {@link ToStringExpression ToStringExpression} instance to call.
 * 
 * <p>Static methods are available to create a new instance from a boolean, double or String respectively, with each having
 * a different way of converting the provided value into the other outputs:
 * <table>
 * <thead>
 *   <tr>
 *     <th>Input</th>
 *     <th>To boolean</th>
 *     <th>To double</th>
 *     <th>To String</th>
 *   </tr>
 * </thead>
 * <tbody>
 *   <tr>
 *     <th>boolean<br></th>
 *     <td>Use as-is<br></td>
 *     <td>Set to 1 if true, else 0</td>
 *     <td>Convert to String using {@link Boolean#toString(boolean)}</td>
 *   </tr>
 *   <tr>
 *     <th>double</th>
 *     <td>Set to true if not 0, else false</td>
 *     <td>Use as-is</td>
 *     <td>Convert to String using {@link Integer#toString(int)} (if integer) or {@link Double#toString(double)}</td>
 *   </tr>
 *   <tr>
 *     <th>String</th>
 *     <td>Convert to boolean using {@link Boolean#parseBoolean(String)}</td>
 *     <td>Convert to double using {@link Double#parseDouble(String)}. Should it fail, use String length instead<br></td>
 *     <td>Use as-is</td>
 *   </tr>
 * </tbody>
 * </table>
 */
public class ConstantExpressionTemplate implements ExpressionTemplate{
    
    private final ToBooleanExpression toBooleanExpression;
    private final ToDoubleExpression toDoubleExpression;
    private final ToStringExpression toStringExpression;
    
    public ConstantExpressionTemplate(boolean booleanValue, double doubleValue, String stringValue){
        this.toBooleanExpression = ToBooleanExpression.literal(booleanValue);
        this.toDoubleExpression = ToDoubleExpression.literal(doubleValue);
        this.toStringExpression = ToStringExpression.literal(stringValue);
    }
    
    /**
     * Creates a new ConstantExpressionTemplate using the provided boolean value.
     * 
     * <p>For {@link ToDoubleExpression ToDoubleExpression's} double value is 1 used for boolean value {@code true} and
     * 0 for boolean value {@code false}.
     * <br>For {@link ToStringExpression ToStringExpression's} String value is {@link Boolean#toString(boolean)} used.
     * 
     * @param  booleanValue
     *         The boolean value to use.
     * 
     * @return new ConstantExpressionTemplate holding the ToBooleanExpression, ToDoubleExpression and ToStringExpression instances.
     */
    public static ConstantExpressionTemplate of(boolean booleanValue){
        return new ConstantExpressionTemplate(booleanValue, booleanValue ? 1 : 0, Boolean.toString(booleanValue));
    }
    
    /**
     * Creates a new ConstantExpressionTemplate using the provided double value.
     * 
     * <p>For {@link ToBooleanExpression ToBooleanExperssion's} boolean value {@code true} is used if the double value
     * is anything but 0, otherwise {@code false}.
     * <br>For {@link ToStringExpression ToStringExpression's} String value {@link Integer#toString(int)} or
     * {@link Double#toString(double)} is used depending on if the double can be cast to integer.
     * 
     * @param  doubleValue
     *         The double value to use.
     *
     * @return new ConstantExpressionTemplate holding the ToBooleanExpression, ToDoubleExpression and ToStringExpression instances.
     */
    public static ConstantExpressionTemplate of(double doubleValue){
        return new ConstantExpressionTemplate(doubleValue != 0, doubleValue, ((int)doubleValue) == doubleValue ? Integer.toString((int)doubleValue) : Double.toString(doubleValue));
    }
    
    /**
     * Creates a new ConstantExpressionTemplate using the provided String value.
     * 
     * <p>For {@link ToBooleanExpression ToBooleanExpression's} boolean value {@link Boolean#parseBoolean(String)} is used.
     * <br>For {@link ToDoubleExpression ToDoubleExpression's} double value {@link Double#parseDouble(String)} is used.
     * In case of an exception happening, the String's length is used instead.
     * 
     * @param  stringValue
     *         The String value to use.
     *
     * @return new ConstantExpressionTemplate holding the ToBooleanExpression, ToDoubleExpression and ToStringExpression instances.
     */
    public static ConstantExpressionTemplate of(String stringValue){
        CheckUtil.notNullOrEmpty(stringValue, ConstantExpressionTemplate.class, "StringValue");
        
        double doubleValue;
        try{
            doubleValue = Double.parseDouble(stringValue);
        }catch(NumberFormatException ex){
            doubleValue = stringValue.length();
        }
        
        return new ConstantExpressionTemplate(Boolean.parseBoolean(stringValue), doubleValue, stringValue);
    }
    
    @Override
    public ToBooleanExpression returnBooleanExpression(){
        return toBooleanExpression;
    }
    
    @Override
    public ToDoubleExpression returnDoubleExpression(){
        return toDoubleExpression;
    }
    
    @Override
    public ToStringExpression returnStringExpression(){
        return toStringExpression;
    }
}
