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

package ch.andre601.expressionparser.expressions;

import ch.andre601.expressionparser.expressions.abstracted.*;
import ch.andre601.expressionparser.internal.CheckUtil;

import java.util.Collection;

/**
 * Class containing a collection of pre-made methods to perform certain operations with.
 * <br>All methods return a ToXExpression class.
 */
public class Expressions{
    
    /**
     * Negates the provided {@link ToBooleanExpression}.
     * 
     * @param  expression
     *         The ToBooleanExpression to negate.
     * 
     * @return ToBooleanExpression with its boolean value negated.
     */
    public static ToBooleanExpression negate(ToBooleanExpression expression){
        CheckUtil.notNull(expression, Expressions.class, "Expression");
        
        return new AbstractUnaryToBooleanExpression<>(expression){
            @Override
            public boolean evaluate(){
                return !delegate.evaluate();
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if and only if all provided ToBooleanExpressions
     * have a true boolean value.
     * 
     * @param  operands
     *         Collection of ToBooleanExpressions to AND together.
     * 
     * @return ToBooleanExpression with boolean value being true if and only if all provided ToBooleanExpressions have
     *         a true boolean value.
     */
    public static ToBooleanExpression and(Collection<ToBooleanExpression> operands){
        CheckUtil.notNullOrEmpty(operands, Expressions.class, "Operands");
        
        return new AbstractToBooleanExpression<>(operands){
            @Override
            public boolean evaluate(){
                for(ToBooleanExpression operand : operands){
                    if(!operand.evaluate())
                        return false;
                }
                
                return true;
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if at least one of the provided ToBooleanExpressions
     * has a true boolean value.
     * 
     * @param  operands
     *         Collection of ToBooleanExpressions to OR together.
     * 
     * @return ToBooleanExpression with boolean value being true if at least one of the provided ToBooleanExpressions has
     *         a true boolean value.
     */
    public static ToBooleanExpression or(Collection<ToBooleanExpression> operands){
        CheckUtil.notNullOrEmpty(operands, Expressions.class, "Operands");
        
        return new AbstractToBooleanExpression<>(operands){
            @Override
            public boolean evaluate(){
                for(ToBooleanExpression operand : operands){
                    if(operand.evaluate())
                        return true;
                }
                
                return false;
            }
        };
    }
    
    /**
     * Returns a {@link ToStringExpression} who's String value is the concatenated (merged) String values of all provided
     * ToStringExpressions.
     * 
     * @param  operands
     *         Collection of ToStringExpressions to concatenate the String values of.
     * 
     * @return ToStringExpression with String value being the concatenated (merged) String values of the provided
     *         ToStringExpression's String values.
     */
    public static ToStringExpression concat(Collection<ToStringExpression> operands){
        CheckUtil.notNullOrEmpty(operands, Expressions.class, "Operands");
        
        return new AbstractToStringExpression<>(operands){
            @Override
            public String evaluate(){
                StringBuilder result = new StringBuilder();
                for(ToStringExpression operand : operands){
                    result.append(operand.evaluate());
                }
                
                return result.toString();
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if and only if the String values of both
     * provided {@link ToStringExpression ToStringExpressions} are equal.
     * <br>This method is case-sensitive. For a case-insensitive option see {@link #equalIgnoreCase(ToStringExpression, ToStringExpression)}.
     * 
     * @param  a
     *         First ToStringExpression to use.
     * @param  b
     *         Second ToStringExpression to compare with.
     * 
     * @return ToBooleanExpression who's boolean value is true if and only if the String values of both provided
     *         ToStringExpressions are equal, including case.
     * 
     * @see #equalIgnoreCase(ToStringExpression, ToStringExpression)
     */
    public static ToBooleanExpression equal(ToStringExpression a, ToStringExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToStringExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToStringExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate().equals(b.evaluate());
            }
        };
    }

    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if and only if the String values of both
     * provided {@link ToStringExpression ToStringExpressions} are equal.
     * <br>This method is case-insensitive. For a case-sensitive option see {@link #equal(ToStringExpression, ToStringExpression)}.
     *
     * @param  a
     *         First ToStringExpression to use.
     * @param  b
     *         Second ToStringExpression to compare with.
     *
     * @return ToBooleanExpression who's boolean value is true if and only if the String values of both provided
     *         ToStringExpressions are equal, including case.
     */
    public static ToBooleanExpression equalIgnoreCase(ToStringExpression a, ToStringExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToStringExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToStringExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate().equalsIgnoreCase(b.evaluate());
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if and only if the String values of both
     * provided {@link ToStringExpression ToStringExpressions} are <b>not</b> equal.
     * <br>This method is case-sensitive. For a case-insensitive option see {@link #notEqualIgnoreCase(ToStringExpression, ToStringExpression)}.
     *
     * @param  a
     *         First ToStringExpression to use.
     * @param  b
     *         Second ToStringExpression to compare with.
     * 
     * @return ToBooleanExpression who's boolean value is true if and only if the String values of both provided
     *         ToStringExpression are <b>not</b> equal, including case.
     */
    public static ToBooleanExpression notEqual(ToStringExpression a, ToStringExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToStringExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToStringExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return !a.evaluate().equals(b.evaluate());
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if and only if the String values of both
     * provided {@link ToStringExpression ToStringExpressions} are <b>not</b> equal.
     * <br>This method is case-sensitive. For a case-insensitive option see {@link #notEqual(ToStringExpression, ToStringExpression)}.
     *
     * @param  a
     *         First ToStringExpression to use.
     * @param  b
     *         Second ToStringExpression to compare with.
     *
     * @return ToBooleanExpression who's boolean value is true if and only if the String values of both provided
     *         ToStringExpression are <b>not</b> equal, including case.
     */
    public static ToBooleanExpression notEqualIgnoreCase(ToStringExpression a, ToStringExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToStringExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToStringExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return !a.evaluate().equalsIgnoreCase(b.evaluate());
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if the String value of the first
     * {@link ToStringExpression} starts with the String value of the second {@link ToStringExpression}.
     * 
     * @param  a
     *         First ToStringExpression to do a check for.
     * @param  b
     *         Second ToStringExpression to check if first starts with.
     * 
     * @return ToBooleanExpression who's boolean value is true if the String value of the first ToStringExpression
     *         starts with the String value of the second ToStringExpression.
     */
    public static ToBooleanExpression startsWith(ToStringExpression a, ToStringExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToStringExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToStringExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate().startsWith(b.evaluate());
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if the String value of the first
     * {@link ToStringExpression} ends with the String value of the second {@link ToStringExpression}.
     *
     * @param  a
     *         First ToStringExpression to do a check for.
     * @param  b
     *         Second ToStringExpression to check if first ends with.
     *
     * @return ToBooleanExpression who's boolean value is true if the String value of the first ToStringExpression
     *         ends with the String value of the second ToStringExpression.
     */
    public static ToBooleanExpression endsWith(ToStringExpression a, ToStringExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToStringExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToStringExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate().endsWith(b.evaluate());
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if the String value of the first
     * {@link ToStringExpression} contains the String value of the second {@link ToStringExpression}.
     *
     * @param  a
     *         First ToStringExpression to do a check for.
     * @param  b
     *         Second ToStringExpression to check if first contains it.
     *
     * @return ToBooleanExpression who's boolean value is true if the String value of the first ToStringExpression
     *         contains the String value of the second ToStringExpression.
     */
    public static ToBooleanExpression contains(ToStringExpression a, ToStringExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToStringExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToStringExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate().contains(b.evaluate());
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if the double value of the first
     * {@link ToDoubleExpression} is greater than the double value of the second {@link ToDoubleExpression}.
     * 
     * @param  a
     *         The first ToDoubleExpression to do a check with.
     * @param  b
     *         The second ToDoubleExpression to do a check with.
     * 
     * @return ToBooleanExpression who's boolean value is true if the double value of the first ToDoubleExpression
     *         is greater than the double value of the second ToDoubleExpression.
     */
    public static ToBooleanExpression greaterThan(ToDoubleExpression a, ToDoubleExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToDoubleExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToDoubleExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate() > b.evaluate();
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if the double value of the first
     * {@link ToDoubleExpression} is greater than or equal to the double value of the second {@link ToDoubleExpression}.
     *
     * @param  a
     *         The first ToDoubleExpression to do a check with.
     * @param  b
     *         The second ToDoubleExpression to do a check with.
     *
     * @return ToBooleanExpression who's boolean value is true if the double value of the first ToDoubleExpression
     *         is greater than or equal to the double value of the second ToDoubleExpression.
     */
    public static ToBooleanExpression greaterOrEqualThan(ToDoubleExpression a, ToDoubleExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToDoubleExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToDoubleExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate() >= b.evaluate();
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if the double value of the first
     * {@link ToDoubleExpression} is less than the double value of the second {@link ToDoubleExpression}.
     *
     * @param  a
     *         The first ToDoubleExpression to do a check with.
     * @param  b
     *         The second ToDoubleExpression to do a check with.
     *
     * @return ToBooleanExpression who's boolean value is true if the double value of the first ToDoubleExpression
     *         is less than the double value of the second ToDoubleExpression.
     */
    public static ToBooleanExpression lessThan(ToDoubleExpression a, ToDoubleExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToDoubleExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToDoubleExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate() < b.evaluate();
            }
        };
    }
    
    /**
     * Returns a {@link ToBooleanExpression} who's boolean value is true if the double value of the first
     * {@link ToDoubleExpression} is less than or equal to the double value of the second {@link ToDoubleExpression}.
     *
     * @param  a
     *         The first ToDoubleExpression to do a check with.
     * @param  b
     *         The second ToDoubleExpression to do a check with.
     *
     * @return ToBooleanExpression who's boolean value is true if the double value of the first ToDoubleExpression
     *         is less than or equal to the double value of the second ToDoubleExpression.
     */
    public static ToBooleanExpression lessOrEqualThan(ToDoubleExpression a, ToDoubleExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToDoubleExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToDoubleExpression");
        
        return new AbstractBinaryToBooleanExpression<>(a, b){
            @Override
            public boolean evaluate(){
                return a.evaluate() <= b.evaluate();
            }
        };
    }
    
    /**
     * Returns a {@link ToDoubleExpression} who's double value is the sum of the double values of all provided
     * ToDoubleExpressions.
     * 
     * @param  operands
     *         Collection of ToDoubleExpression to sum.
     * 
     * @return ToDoubleExpression who's double value is the sum of all double values of the provided ToDoubleExpressions.
     */
    public static ToDoubleExpression sum(Collection<ToDoubleExpression> operands){
        CheckUtil.notNullOrEmpty(operands, Expressions.class, "Operands");
        
        return new AbstractToDoubleExpression<>(operands){
            @Override
            public double evaluate(){
                double result = 0;
                for(ToDoubleExpression operand : operands){
                    result += operand.evaluate();
                }
                
                return result;
            }
        };
    }
    
    /**
     * Returns a {@link ToDoubleExpression} who's double value is the product of all double values from the provided
     * ToDoubleExpressions.
     * 
     * @param  operands
     *         Collection of ToDoubleExpressions to multiply.
     * 
     * @return ToDoubleExpression who's double value is the product of all double values from the provided
     *         ToDoubleExpression.
     */
    public static ToDoubleExpression product(Collection<ToDoubleExpression> operands){
        CheckUtil.notNullOrEmpty(operands, Expressions.class, "Operands");
        
        return new AbstractToDoubleExpression<>(operands){
            @Override
            public double evaluate(){
                double result = 1;
                for(ToDoubleExpression operand : operands){
                    result *= operand.evaluate();
                }
                
                return result;
            }
        };
    }
    
    /**
     * Returns a {@link ToDoubleExpression} who's double value is the double value of the first ToDoubleExpression
     * minus the double value of the second ToDoubleExpression.
     * 
     * @param  a
     *         First ToDoubleExpression who's double value to use.
     * @param  b
     *         Second ToDoubleExpression who's double value to subtract from the first ToDoubleExpression's double value.
     * 
     * @return ToDoubleExpression who's double value is the double value of the first ToDoubleExpression minus the double
     *         value of the second ToDoubleExpression.
     */
    public static ToDoubleExpression sub(ToDoubleExpression a, ToDoubleExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToDoubleExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToDoubleExpression");
        
        return new AbstractBinaryToDoubleExpression<>(a, b){
            @Override
            public double evaluate(){
                return a.evaluate() - b.evaluate();
            }
        };
    }
    
    /**
     * Returns a {@link ToDoubleExpression} who's double value is the double value of the first ToDoubleExpression
     * divided by the double value of the second ToDoubleExpression.
     * 
     * @param  a
     *         First ToDoubleExpression who's double value to use.
     * @param  b
     *         Second ToDoubleExpression who's double value to divide the double value of the first ToDoubleExpression with.
     * 
     * @return ToDoubleExpression who's double value is the double value of the first ToDoubleExpression divided by the
     *         double value of the second ToDoubleExpression.
     */
    public static ToDoubleExpression div(ToDoubleExpression a, ToDoubleExpression b){
        CheckUtil.notNull(a, Expressions.class, "First ToDoubleExpression");
        CheckUtil.notNull(b, Expressions.class, "Second ToDoubleExpression");
        
        return new AbstractBinaryToDoubleExpression<>(a, b){
            @Override
            public double evaluate(){
                return a.evaluate() / b.evaluate();
            }
        };
    }
    
    /**
     * Negates the provided {@link ToDoubleExpression ToDoubleExpression's} double value.
     * 
     * @param  a
     *         The ToDoubleExpression to negate the double value of.
     * 
     * @return ToDoubleExpression who's double value has been negated.
     */
    public static ToDoubleExpression negateNumber(ToDoubleExpression a){
        CheckUtil.notNull(a, Expressions.class, "First ToDoubleExpression");
        
        return new AbstractUnaryToDoubleExpression<>(a){
            @Override
            public double evaluate(){
                return -delegate.evaluate();
            }
        };
    }
}
