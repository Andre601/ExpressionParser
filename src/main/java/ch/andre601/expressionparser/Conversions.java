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

package ch.andre601.expressionparser;

import ch.andre601.expressionparser.expressions.ToBooleanExpression;
import ch.andre601.expressionparser.expressions.ToDoubleExpression;
import ch.andre601.expressionparser.expressions.ToStringExpression;
import ch.andre601.expressionparser.expressions.abstracted.AbstractUnaryToBooleanExpression;
import ch.andre601.expressionparser.expressions.abstracted.AbstractUnaryToDoubleExpression;
import ch.andre601.expressionparser.expressions.abstracted.AbstractUnaryToStringExpression;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Class containing static methods to convert {@link ToBooleanExpression}, {@link ToDoubleExpression} and
 * {@link ToStringExpression} into their respective counterparts.
 */
public class Conversions{
    
    /**
     * Static {@link NumberFormat} instance to parse Strings with.
     */
    private final static NumberFormat NUMBER_FORMAT;
    
    static {
        NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.ROOT);
        NUMBER_FORMAT.setGroupingUsed(false);
    }
    
    /**
     * Converts the provided {@link ToDoubleExpression} into a {@link ToBooleanExpression} with its boolean value being
     * true if the ToDoubleExpression's double value is anything but zero.
     * 
     * @param  expression
     *         The ToDoubleExpression to convert.
     * 
     * @return ToBooleanExpression who's boolean value is true if the ToDoubleExpression's double value is not zero.
     */
    public static ToBooleanExpression toBoolean(ToDoubleExpression expression){
        return new AbstractUnaryToBooleanExpression<>(expression) {
            @Override
            public boolean evaluate(){
                return expression.evaluate() != 0;
            }
        };
    }
    
    /**
     * Converts the provided {@link ToStringExpression} into a {@link ToBooleanExpression} with its boolean value being
     * the output of {@link Boolean#parseBoolean(String) Boolean.parseBoolean(expression.evaluate())}.
     * 
     * @param  expression
     *         The ToStringExpression to convert.
     * 
     * @return ToBooleanExpression who's boolean value is the output of {@link Boolean#parseBoolean(String)}.
     */
    public static ToBooleanExpression toBoolean(ToStringExpression expression){
        return new AbstractUnaryToBooleanExpression<>(expression) {
            @Override
            public boolean evaluate(){
                return Boolean.parseBoolean(delegate.evaluate());
            }
        };
    }
    
    /**
     * Converts the provided {@link ToBooleanExpression} into a {@link ToDoubleExpression} with its double value being
     * one should the ToBooleanExpression's boolean value be true, else zero.
     * 
     * @param  expression
     *         The ToBooleanExpression to convert.
     * 
     * @return ToDoubleExpression with its double value being one if the ToBooleanExpression's boolean value be true, else zero.
     */
    public static ToDoubleExpression toDouble(ToBooleanExpression expression){
        return new AbstractUnaryToDoubleExpression<>(expression) {
            @Override
            public double evaluate(){
                return delegate.evaluate() ? 1 : 0;
            }
        };
    }
    
    /**
     * Converts the provided {@link ToStringExpression} into a {@link ToDoubleExpression} with its double value being parsed
     * from the ToStringExpression's String value using a {@link #NUMBER_FORMAT static NumberFormatter}.
     * <br>Should the parsing fail will the String value's length be used instead.
     * 
     * @param  expression
     *         The ToStringExpression to convert.
     * 
     * @return ToDoubleExpression who's double value either matches the ToStringExpression's String value text, if showing
     *         a number, else the String's text length.
     */
    public static ToDoubleExpression toDouble(ToStringExpression expression){
        return new AbstractUnaryToDoubleExpression<>(expression) {
            @Override
            public double evaluate(){
                String result = delegate.evaluate();
                try{
                    return NUMBER_FORMAT.parse(result).doubleValue();
                }catch(ParseException | NumberFormatException ex){
                    return result.length();
                }
            }
        };
    }
    
    /**
     * Converts the provided {@link ToBooleanExpression} into a {@link ToStringExpression} with its String value being
     * the output of {@link Boolean#toString(boolean) Boolean.toString(expression.evaluate())}.
     * 
     * @param  expression
     *         The ToBooleanExpression to convert.
     * 
     * @return ToStringExpression who's String value is the output of {@link Boolean#toString(boolean)}.
     */
    public static ToStringExpression toString(ToBooleanExpression expression){
        return new AbstractUnaryToStringExpression<>(expression) {
            @Override
            public String evaluate(){
                return Boolean.toString(delegate.evaluate());
            }
        };
    }
    
    /**
     * Converts the provided {@link ToDoubleExpression} into a {@link ToStringExpression} with its String value being
     * the output of either {@link Integer#toString(int) Integer.toString((int)expression.evaluate())} or
     * {@link Double#toString(double) Double.toString(expression.evaluate())} depending on if the
     * ToDoubleExpression's double value can be cast to an Integer.
     * 
     * @param  expression
     *         The ToDoubleExpression to convert.
     * 
     * @return ToStringExpression who's String value is the output of either {@link Integer#toString(int)} or {@link Double#toString(double)}.
     */
    public static ToStringExpression toString(ToDoubleExpression expression){
        return new AbstractUnaryToStringExpression<>(expression) {
            @Override
            public String evaluate(){
                double result = delegate.evaluate();
                return result == (int)result ? Integer.toString((int)result) : Double.toString(result);
            }
        };
    }
}
