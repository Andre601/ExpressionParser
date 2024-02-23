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
import ch.andre601.expressionparser.internal.CheckUtil;
import ch.andre601.expressionparser.templates.ExpressionTemplate;
import ch.andre601.expressionparser.templates.ExpressionTemplates;
import ch.andre601.expressionparser.tokens.Token;

import java.util.List;

/**
 * {@link ValueReader} creating a negated {@link ExpressionTemplate} should the first token be the provided one.
 */
public class NegatedNumberReader extends ValueReader{
    
    private final Token negationToken;
    
    /**
     * Sets the {@link Token} that this ValueReader should look for.
     *
     * @param negationToken
     *        Token this ValueReader should look for.
     */
    public NegatedNumberReader(Token negationToken){
        CheckUtil.notNull(negationToken, NegatedNumberReader.class, "NegationToken");
        
        this.negationToken = negationToken;
    }
    
    @Override
    public ExpressionTemplate read(ExpressionTemplateParser parser, List<Token> tokens, ParseWarnCollector collector){
        if(tokens.get(0) == negationToken){
            tokens.remove(0);
            return ExpressionTemplates.negateNumber(parser.parse(tokens, collector));
        }
        
        return null;
    }
}
