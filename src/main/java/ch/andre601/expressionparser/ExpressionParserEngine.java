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

import ch.andre601.expressionparser.templates.ExpressionTemplate;

/**
 * This interface can be used to create multiple Expression parsers, all using the same core stuff.
 * <br>It is not required to use this at all.
 */
public interface ExpressionParserEngine{
    
    /**
     * Converts the provided String into a {@link ExpressionTemplate ExpressionTemplate instance} to use.
     * 
     * @param  text
     *         The text to parse into a ExpressionTemplate.
     * @param  collector
     *         {@link ParseWarnCollector ParseWarnCollector instance} used to collect warnings during the parsing.
     * 
     * @return Possibly-null ExpressionTemplate instance.
     */
    ExpressionTemplate compile(String text, ParseWarnCollector collector);
}
