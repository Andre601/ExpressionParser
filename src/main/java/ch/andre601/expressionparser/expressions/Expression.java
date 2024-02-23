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

/**
 * Empty interface used to extend other interfaces with.
 * 
 * <p>It's recommended to use a pre-existing interface such as {@link ToBooleanExpression ToBooleanExpression},
 * {@link ToDoubleExpression ToDoubleExpression} or {@link ToStringExpression}, but you can also create your own
 * implementations to use as long as they extend/implement this interface.
 */
public interface Expression{}
