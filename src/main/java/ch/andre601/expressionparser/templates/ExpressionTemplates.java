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

import ch.andre601.expressionparser.expressions.Expressions;
import ch.andre601.expressionparser.expressions.ToBooleanExpression;
import ch.andre601.expressionparser.expressions.ToDoubleExpression;
import ch.andre601.expressionparser.expressions.ToStringExpression;
import ch.andre601.expressionparser.templates.abstracted.AbstractBooleanExpressionTemplate;
import ch.andre601.expressionparser.templates.abstracted.AbstractDoubleExpressionTemplate;
import ch.andre601.expressionparser.templates.abstracted.AbstractStringExpressionTemplate;

import java.util.Collection;

/**
 * Class containing a collection of pre-made {@link ExpressionTemplate} actions.
 */
public class ExpressionTemplates{
    
    /**
     * Negates the provided {@link ExpressionTemplate ExpressionTemplate's} output.
     * 
     * @param  template
     *         The ExpressionTemplate to negate.
     * 
     * @return ExpressionTemplate with its value negated.
     */
    public static ExpressionTemplate negate(ExpressionTemplate template){
        return new Negation(template);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true if and only if all provided
     * {@link ExpressionTemplate ExpressionTemplates} return true.
     * 
     * @param  operands
     *         Collection of ExpressionTemplates to chain.
     * 
     * @return ExpressionTemplate who's boolean value returns true if and only if all provided ExpressionTemplates return true.
     */
    public static ExpressionTemplate and(Collection<ExpressionTemplate> operands){
        return new And(operands);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true if any of the provided {@link ExpressionTemplate ExpressionTemplates}
     * returns true.
     * 
     * @param  operands
     *         Collection of ExpressionTemplates to chain.
     * 
     * @return ExpressionTemplate who's boolean value returns true when any of the provided ExpressionTemplates returns true.
     */
    public static ExpressionTemplate or(Collection<ExpressionTemplate> operands){
        return new Or(operands);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} with the String values of the provided {@link ExpressionTemplate ExpressionTemplates}
     * merged together.
     * 
     * @param  operands
     *         Collection of ExpressionTemplates to merge String values of.
     * 
     * @return ExpressionTemplate with the String values of all provided ExpressionTemplates merged together.
     */
    public static ExpressionTemplate concat(Collection<ExpressionTemplate> operands){
        return new Concatenate(operands);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value is true if and only if the two provided {@link ExpressionTemplate ExpressionTemplates}
     * do have equal String values.
     * <br>This method is case-sensitive. For a case-insensitive option, use {@link #equalIgnoreCase(ExpressionTemplate, ExpressionTemplate) equalIgnoreCase}.
     * 
     * @param  a
     *         The first ExpressionTemplate to check for equality.
     * @param  b
     *         The second ExpressionTemplate to check for equality.
     * 
     * @return ExpressionTemplate who's boolean value is true if and only if both ExpressionTemplate's String value is equal.
     * 
     * @see #equalIgnoreCase(ExpressionTemplate, ExpressionTemplate)
     */
    public static ExpressionTemplate equal(ExpressionTemplate a, ExpressionTemplate b){
        return new Equal(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value is true if and only if the two provided {@link ExpressionTemplate ExpressionTemplates}
     * do <b>not</b> have equal String values.
     * <br>This method is case-sensitive. For a case-insensitive option, use {@link #notEqualIgnoreCase(ExpressionTemplate, ExpressionTemplate) notEqualIgnoreCase}.
     * 
     * @param  a
     *         The first ExpressionTemplate to check for equality.
     * @param  b
     *         The second ExpressionTemplate to check for equality.
     * 
     * @return ExpressionTemplate who's boolean value is true if and only if both ExpressionTemplate's String values are <b>not</b> equal.
     * 
     * @see #notEqualIgnoreCase(ExpressionTemplate, ExpressionTemplate) 
     */
    public static ExpressionTemplate notEqual(ExpressionTemplate a, ExpressionTemplate b){
        return new NotEqual(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value is true if and only if the two provided {@link ExpressionTemplate ExpressionTemplates}
     * do have equal String values.
     * <br>This method is case-insensitive. For a case-insensitive option, use {@link #equal(ExpressionTemplate, ExpressionTemplate) equal}.
     *
     * @param  a
     *         The first ExpressionTemplate to check for equality.
     * @param  b
     *         The second ExpressionTemplate to check for equality.
     * 
     * @return ExpressionTemplate who's boolean value is true if and only if both ExpressionTemplate's String value is equal.
     *
     * @see #equal(ExpressionTemplate, ExpressionTemplate)
     */
    public static ExpressionTemplate equalIgnoreCase(ExpressionTemplate a, ExpressionTemplate b){
        return new EqualIgnoreCase(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value is true if and only if the two provided {@link ExpressionTemplate ExpressionTemplates}
     * do <b>not</b> have equal String values.
     * <br>This method is case-sensitive. For a case-insensitive option, use {@link #notEqual(ExpressionTemplate, ExpressionTemplate) notEqual}.
     *
     * @param  a
     *         The first ExpressionTemplate to check for equality.
     * @param  b
     *         The second ExpressionTemplate to check for equality.
     * 
     * @return ExpressionTemplate who's boolean value is true if and only if both ExpressionTemplate's String values are <b>not</b> equal.
     * 
     * @see #notEqual(ExpressionTemplate, ExpressionTemplate)
     */
    public static ExpressionTemplate notEqualIgnoreCase(ExpressionTemplate a, ExpressionTemplate b){
        return new NotEqualIgnoreCase(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true when the String value of the first ExpressionTemplate
     * starts with the String value of the second ExpressionTemplate.
     * 
     * @param  a
     *         The first ExpressionTemplate to check the String value of.
     * @param  b
     *         The second ExpressionTemplate with the String value to look for in the first ExpressionTemplate.
     * 
     * @return True if the first ExpressionTemplate's String value starts with the String value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate startsWith(ExpressionTemplate a, ExpressionTemplate b){
        return new StartsWith(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true when the String value of the first ExpressionTemplate
     * ends with the String value of the second ExpressionTemplate.
     *
     * @param  a
     *         The first ExpressionTemplate to check the String value of.
     * @param  b
     *         The second ExpressionTemplate with the String value to look for in the first ExpressionTemplate.
     * 
     * @return True if the first ExpressionTemplate's String value ends with the String value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate endsWith(ExpressionTemplate a, ExpressionTemplate b){
        return new EndsWith(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true when the String value of the first ExpressionTemplate
     * contains the String value of the second ExpressionTemplate.
     * 
     * @param  a
     *         The first ExpressionTemplate to check the String value of.
     * @param  b
     *         The second ExpressionTemplate with the String value to look for in the first ExpressionTemplate.
     * 
     * @return True if the first ExpressionTemplate's String value contains the String value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate contains(ExpressionTemplate a, ExpressionTemplate b){
        return new Contains(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true when the double value of the first
     * ExpressionTemplate is larger than the double value of the second ExpressionTemplate.
     *
     * @param  a
     *         The first ExpressionTemplate to do a check with.
     * @param  b
     *         The second ExpressionTemplate to do a check with.
     * 
     * @return ExpressionTemplate who's boolean value returns true when the double value of the first ExpressionTemplate
     *         is larger than the double value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate greater(ExpressionTemplate a, ExpressionTemplate b){
        return new Greater(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true when the double value of the first
     * ExpressionTemplate is larger than or equal to the double value of the second ExpressionTemplate.
     *
     * @param  a
     *         The first ExpressionTemplate to do a check with.
     * @param  b
     *         The second ExpressionTemplate to do a check with.
     * 
     * @return ExpressionTemplate who's boolean value returns true when the double value of the first ExpressionTemplate
     *         is larger than or equal to the double value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate greaterOrEqual(ExpressionTemplate a, ExpressionTemplate b){
        return new GreaterOrEqual(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true when the double value of the first
     * ExpressionTemplate is less than the double value of the second ExpressionTemplate.
     *
     * @param  a
     *         The first ExpressionTemplate to do a check with.
     * @param  b
     *         The second ExpressionTemplate to do a check with.
     * 
     * @return ExpressionTemplate who's boolean value returns true when the double value of the first ExpressionTemplate
     *         is less than the double value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate less(ExpressionTemplate a, ExpressionTemplate b){
        return new Less(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's boolean value returns true when the double value of the first
     * ExpressionTemplate is less than or equal to the double value of the second ExpressionTemplate.
     *
     * @param  a
     *         The first ExpressionTemplate to do a check with.
     * @param  b
     *         The second ExpressionTemplate to do a check with.
     * 
     * @return ExpressionTemplate who's boolean value returns true when the double value of the first ExpressionTemplate
     *         is less than or equal to the double value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate lessOrEqual(ExpressionTemplate a, ExpressionTemplate b){
        return new LessOrEqual(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's double value is the sum of all double values from the provided
     * ExpressionTemplates.
     *
     * @param  operands
     *         Collection of ExpressionTemplates to sum.
     * 
     * @return ExpressionTemplate who's double value is the sum of all double values of the provided ExpressionTemplates.
     */
    public static ExpressionTemplate sum(Collection<ExpressionTemplate> operands){
        return new Sum(operands);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's double value is the product of all double values from the provided
     * ExpressionTemplates.
     *
     * @param  operands
     *         Collection of ExpressionTemplates to sum.
     *
     * @return ExpressionTemplate who's double value is the product of all double values of the provided ExpressionTemplates.
     */
    public static ExpressionTemplate product(Collection<ExpressionTemplate> operands){
        return new Product(operands);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's double value is the double value of the first ExpressionTemplate
     * minus the double value of the second ExpressionTemplate.
     *
     * @param  a
     *         First ExpressionTemplate who's double value to use.
     * @param  b
     *         Second ExpressionTemplate who's double value to subtract from the first ExpressionTemplate's double value.
     *
     * @return ExpressionTemplate who's double value is the double value of the first ExpressionTemplate minus the double
     *         value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate sub(ExpressionTemplate a, ExpressionTemplate b){
        return new Sub(a, b);
    }
    
    /**
     * Returns a {@link ExpressionTemplate} who's double value is the double value of the first ExpressionTemplate
     * divided by the double value of the second ExpressionTemplate.
     *
     * @param  a
     *         First ExpressionTemplate who's double value to use.
     * @param  b
     *         Second ExpressionTemplate who's double value to divide the double value of the first ExpressionTemplate with.
     *
     * @return ExpressionTemplate who's double value is the double value of the first ExpressionTemplate divided by the
     *         double value of the second ExpressionTemplate.
     */
    public static ExpressionTemplate div(ExpressionTemplate a, ExpressionTemplate b){
        return new Div(a, b);
    }
    
    /**
     * Negates the provided {@link ExpressionTemplate ExpressionTemplate's} double value.
     *
     * @param  template
     *         The ExpressionTemplate to negate the double value of.
     *
     * @return ExpressionTemplate who's double value has been negated.
     */
    public static ExpressionTemplate negateNumber(ExpressionTemplate template){
        return new NegationNumber(template);
    }
    
    private static class Negation extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate template;
        
        Negation(ExpressionTemplate template){
            this.template = template;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.negate(template.returnBooleanExpression());
        }
    }
    
    private static class And extends AbstractBooleanExpressionTemplate{
        
        private final Collection<ExpressionTemplate> operands;
        
        And(Collection<ExpressionTemplate> operands){
            this.operands = operands;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.and(operands.stream().map(ExpressionTemplate::returnBooleanExpression).toList());
        }
    }
    
    private static class Or extends AbstractBooleanExpressionTemplate{
        
        private final Collection<ExpressionTemplate> operands;
        
        Or(Collection<ExpressionTemplate> operands){
            this.operands = operands;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.or(operands.stream().map(ExpressionTemplate::returnBooleanExpression).toList());
        }
    }
    
    private static class Concatenate extends AbstractStringExpressionTemplate{
        
        private final Collection<ExpressionTemplate> operands;
        
        Concatenate(Collection<ExpressionTemplate> operands){
            this.operands = operands;
        }
        
        @Override
        public ToStringExpression returnStringExpression(){
            return Expressions.concat(operands.stream().map(ExpressionTemplate::returnStringExpression).toList());
        }
    }
    
    private static class Equal extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        Equal(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.equal(a.returnStringExpression(), b.returnStringExpression());
        }
    }
    
    private static class NotEqual extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        NotEqual(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.notEqual(a.returnStringExpression(), b.returnStringExpression());
        }
    }
    
    private static class EqualIgnoreCase extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        EqualIgnoreCase(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.equalIgnoreCase(a.returnStringExpression(), b.returnStringExpression());
        }
    }
    
    private static class NotEqualIgnoreCase extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        NotEqualIgnoreCase(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.notEqualIgnoreCase(a.returnStringExpression(), b.returnStringExpression());
        }
    }
    
    private static class StartsWith extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        private StartsWith(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.startsWith(a.returnStringExpression(), b.returnStringExpression());
        }
    }
    
    private static class EndsWith extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        private EndsWith(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.endsWith(a.returnStringExpression(), b.returnStringExpression());
        }
    }
    
    private static class Contains extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        private Contains(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.contains(a.returnStringExpression(), b.returnStringExpression());
        }
    }
    
    private static class Greater extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        Greater(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.greaterThan(a.returnDoubleExpression(), b.returnDoubleExpression());
        }
    }
    
    private static class GreaterOrEqual extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        GreaterOrEqual(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.greaterOrEqualThan(a.returnDoubleExpression(), b.returnDoubleExpression());
        }
    }
    
    private static class Less extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        Less(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.lessThan(a.returnDoubleExpression(), b.returnDoubleExpression());
        }
    }
    
    private static class LessOrEqual extends AbstractBooleanExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        LessOrEqual(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToBooleanExpression returnBooleanExpression(){
            return Expressions.lessOrEqualThan(a.returnDoubleExpression(), b.returnDoubleExpression());
        }
    }
    
    private static class Sum extends AbstractDoubleExpressionTemplate{
        
        private final Collection<ExpressionTemplate> operands;
        
        Sum(Collection<ExpressionTemplate> operands){
            this.operands = operands;
        }
        
        @Override
        public ToDoubleExpression returnDoubleExpression(){
            return Expressions.sum(operands.stream().map(ExpressionTemplate::returnDoubleExpression).toList());
        }
    }
    
    private static class Product extends AbstractDoubleExpressionTemplate{
        
        private final Collection<ExpressionTemplate> operands;
        
        Product(Collection<ExpressionTemplate> operands){
            this.operands = operands;
        }
        
        @Override
        public ToDoubleExpression returnDoubleExpression(){
            return Expressions.product(operands.stream().map(ExpressionTemplate::returnDoubleExpression).toList());
        }
    }
    
    private static class Sub extends AbstractDoubleExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        Sub(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToDoubleExpression returnDoubleExpression(){
            return Expressions.sub(a.returnDoubleExpression(), b.returnDoubleExpression());
        }
    }
    
    private static class Div extends AbstractDoubleExpressionTemplate{
        
        private final ExpressionTemplate a;
        private final ExpressionTemplate b;
        
        Div(ExpressionTemplate a, ExpressionTemplate b){
            this.a = a;
            this.b = b;
        }
        
        @Override
        public ToDoubleExpression returnDoubleExpression(){
            return Expressions.div(a.returnDoubleExpression(), b.returnDoubleExpression());
        }
    }
    
    private static class NegationNumber extends AbstractDoubleExpressionTemplate{
        
        private final ExpressionTemplate template;
        
        NegationNumber(ExpressionTemplate template){
            this.template = template;
        }
        
        @Override
        public ToDoubleExpression returnDoubleExpression(){
            return Expressions.negateNumber(template.returnDoubleExpression());
        }
    }
}
