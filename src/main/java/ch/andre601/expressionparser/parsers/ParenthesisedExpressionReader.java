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
import ch.andre601.expressionparser.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ValueReader} converting a set of token into an {@link ExpressionTemplate} if the first token is the same
 * as the first token provided and the follow-up tokens containing the second provided token.
 */
public class ParenthesisedExpressionReader extends ValueReader{
    
    private final Token openingParenthesis;
    private final Token closingParenthesis;
    
    /**
     * Sets the {@link Token tokens} to look for when parsing.
     * 
     * @param openingParenthesis
     *        First token to look for representing an opening parenthesis.
     * @param closingParenthesis
     *        Second token to look for representing a closing parenthesis.
     */
    public ParenthesisedExpressionReader(Token openingParenthesis, Token closingParenthesis){
        CheckUtil.notNull(openingParenthesis, ParenthesisedExpressionReader.class, "Opening Parenthesis");
        CheckUtil.notNull(closingParenthesis, ParenthesisedExpressionReader.class, "Closing Parenthesis");
        
        this.openingParenthesis = openingParenthesis;
        this.closingParenthesis = closingParenthesis;
    }
    
    @Override
    public ExpressionTemplate read(ExpressionTemplateParser parser, List<Token> tokens, ParseWarnCollector collector){
        if(tokens.get(0) == openingParenthesis){
            int index = 0;
            int cnt = 1;
            do {
                index += 1;
                if(tokens.size() <= index)
                    return null;
                
                Token token = tokens.get(index);
                if(token == openingParenthesis){
                    cnt++;
                }else
                if(token == closingParenthesis){
                    cnt--;
                }
            }while(cnt != 0);
            
            ExpressionTemplate result = parser.parse(new ArrayList<>(tokens.subList(1, index)), collector);
            for(int i = 0; i <= index; i++){
                tokens.remove(0);
            }
            
            return result;
        }
        
        return null;
    }
}
