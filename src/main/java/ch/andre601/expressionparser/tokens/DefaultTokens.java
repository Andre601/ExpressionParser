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

package ch.andre601.expressionparser.tokens;

/**
 * Class containing a collection of pre-made {@link Token Tokens}.
 * <br>These are used by the library in the {@link ch.andre601.expressionparser.DefaultExpressionParserEngine.Builder#createDefault() createDefault()}
 * method of the {@link ch.andre601.expressionparser.DefaultExpressionParserEngine.Builder Builder class}.
 */
public class DefaultTokens{
    
    public static final Token AND = new Token("AND");
    public static final Token OR = new Token("OR");
    
    public static final Token OPENING_PARENTHESIS = new Token("OPENING_PARENTHESIS");
    public static final Token CLOSING_PARENTHESIS = new Token("CLOSING_PARENTHESIS");
    
    public static final Token EQUAL = new Token("EQUAL");
    public static final Token NOT_EQUAL = new Token("NOT_EQUAL");
    public static final Token EQUAL_IGNORE_CASE = new Token("EQUAL_IGNORE_CASE");
    public static final Token NOT_EQUAL_IGNORE_CASE = new Token("NOT_EQUAL_IGNORE_CASE");
    public static final Token STARTS_WITH = new Token("STARTS_WITH");
    public static final Token ENDS_WITH = new Token("ENDS_WITH");
    public static final Token CONTAINS = new Token("CONTAINS");
    
    public static final Token NEGATION = new Token("NEGATION");
    
    public static final Token GREATER_THAN = new Token("GREATER_THAN");
    public static final Token GREATER_OR_EQUAL_THAN = new Token("GREATER_OR_EQUAL_THAN");
    public static final Token LESS_THAN = new Token("LESS_THAN");
    public static final Token LESS_OR_EQUAL_THAN = new Token("LESS_OR_EQUAL_THAN");
    
    public static final Token CONCAT_STRING = new Token("CONCAT_STRING");
    
    public static final Token ADD = new Token("ADD");
    public static final Token SUBTRACT = new Token("SUBTRACT");
    public static final Token MULTIPLY = new Token("MULTIPLY");
    public static final Token DIVIDE = new Token("DIVIDE");
}
