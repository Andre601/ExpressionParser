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

package ch.andre601.expressionparser.parsers;

import ch.andre601.expressionparser.ParseWarnCollector;
import ch.andre601.expressionparser.templates.ExpressionTemplate;
import ch.andre601.expressionparser.tokens.Token;

import java.util.List;

/**
 * Abstract class used in other classes to create a ValueReader.
 * <br>The Value reader goes through the collection of {@link Token Tokens} and tries to convert them into a
 * {@link ExpressionTemplate} should the token match a specific one.
 * 
 * @see BooleanConstantReader
 * @see NegatedExpressionReader
 * @see NegatedNumberReader
 * @see NumberConstantReader
 * @see ParenthesisedExpressionReader
 * @see StringConstantReader
 */
public abstract class ValueReader{
    
    /**
     * Method called by the {@link ExpressionTemplateParser} to convert a List of {@link Token} into a single
     * {@link ExpressionTemplate}.
     * 
     * @param  parser
     *         ExpressionTemplateParser instance to use. The default ExpressionTemplateParser provides itself here.
     * @param  tokens
     *         List of tokens to parse into an ExpressionTemplate.
     * @param  collector
     *         {@link ParseWarnCollector} instance to use
     * 
     * @return Possibly-null ExpressionTemplate.
     */
    public abstract ExpressionTemplate read(ExpressionTemplateParser parser, List<Token> tokens, ParseWarnCollector collector);
}
