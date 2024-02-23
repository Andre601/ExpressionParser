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

import ch.andre601.expressionparser.internal.CheckUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple class that is used by the {@link DefaultExpressionParserEngine} and the
 * {@link ch.andre601.expressionparser.expressions.ExpressionTokenizer} for when an issue appears.
 * <br>Errors will be collected together and returned using this ParseWarnCollector class. What happens with the collected
 * warnings is up to you to decide.
 */
public class ParseWarnCollector{
    
    private final String expression;
    private final List<Context> warnings = new ArrayList<>();
    
    /**
     * Creates a new instance of the ParseWarnCollector, with the expression it is used on set.
     * 
     * @param expression
     *        Not-null String containing the expression this ParseWarnCollector is used for.
     * 
     * @throws IllegalArgumentException
     *         Should the provided String be null or empty.
     */
    public ParseWarnCollector(String expression){
        CheckUtil.notNullOrEmpty(expression, ParseWarnCollector.class, "Expression");
        
        this.expression = expression;
    }
    
    /**
     * Adds a new warning without an exact position within the text. This method uses the
     * {@link String#format(String, Object...) format method} of the String class to replace placeholders within the String.
     * 
     * @param warning
     *        The warning to add. May contain String.format compatible placeholders.
     * @param args
     *        Optional array of values to put into the String.
     */
    public void appendWarningFormatted(String warning, Object... args){
        appendWarningFormatted(-1, warning, args);
    }
    
    /**
     * Adds a new warning without an exact position within the text.
     * 
     * @param warning
     *        The warning to add.
     */
    public void appendWarning(String warning){
        appendWarning(-1, warning);
    }
    
    /**
     * Adds a new warning with a specific position of where the error appeared. This method uses the
     * {@link String#format(String, Object...) format method} of the String class to replace placeholders within the String.
     * 
     * @param position
     *        The position in the expression String for where the issue happened.
     * @param warning
     *        The warning to add. May contain String.format compatible placeholders.
     * @param args
     *        Optional array of values to put into the String.
     */
    public void appendWarningFormatted(int position, String warning, Object... args){
        CheckUtil.notNullOrEmpty(warning, ParseWarnCollector.class, "Warning");
        CheckUtil.noneNull(ParseWarnCollector.class, "Args", args);
        
        appendWarning(position, String.format(warning, args));
    }
    
    public String getExpression(){
        return expression;
    }
    
    /**
     * Adds a new warning with a specific position of where the error appeared.
     *
     * @param position
     *        The position in the expression String for where the issue happened.
     * @param warning
     *        The warning to add.
     */
    public void appendWarning(int position, String warning){
        CheckUtil.notNullOrEmpty(warning, ParseWarnCollector.class, "Warning");
        
        warnings.add(new Context(position, warning));
    }
    
    /**
     * Returns whether the ParseWarnCollector has collected any warnings.
     * 
     * @return True if ParseWarnCollector has any warnings, false otherwise.
     */
    public boolean hasWarnings(){
        return !getWarnings().isEmpty();
    }
    
    /**
     * Returns a List of {@link Context Context instances} which may contain a position and warning.
     * 
     * @return Possibly-empty List of Context instances.
     */
    public List<Context> getWarnings(){
        return warnings;
    }
    
    /**
     * Record used to hold the position of where an issue appeared and the warning itself.
     * <br>In case of {@link #appendWarning(String)} or {@link #appendWarningFormatted(String, Object...)} being used
     * will the position be {@code -1}.
     * 
     * @param position
     *        The position of where the issue happened in the expression.
     * @param message
     *        The message to include.
     */
    public record Context(int position, String message){}
}
