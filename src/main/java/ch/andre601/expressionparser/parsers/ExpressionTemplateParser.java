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
import ch.andre601.expressionparser.operator.ListOperator;
import ch.andre601.expressionparser.operator.Operator;
import ch.andre601.expressionparser.templates.ExpressionTemplate;
import ch.andre601.expressionparser.templates.ExpressionTemplates;
import ch.andre601.expressionparser.tokens.Token;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to parse a list of {@link Token Tokens} into a single {@link ExpressionTemplate}.
 */
public class ExpressionTemplateParser{
    
    private final ImmutableMap<Token, Operator> operators;
    private final ImmutableList<ValueReader> valueReaders;
    
    /**
     * Creates a new instance of this class which uses the provided Map of {@link Token Tokens} and their {@link Operator Operators}
     * and also the provided list of {@link ValueReader ValueReaders}.
     * 
     * @param operators
     *        ImmutableMap of Tokens and their Operators.
     * @param valueReaders
     *        ImmutableList of ValueReaders.
     */
    public ExpressionTemplateParser(ImmutableMap<Token, Operator> operators, ImmutableList<ValueReader> valueReaders){
        CheckUtil.notNullOrEmpty(operators, ExpressionTemplateParser.class, "Operators");
        CheckUtil.notNullOrEmpty(valueReaders, ExpressionTemplateParser.class, "ValueReaders");
        
        this.operators = operators;
        this.valueReaders = valueReaders;
    }
    
    /**
     * Takes a list of {@link Token Tokens} and converts them into a {@link ExpressionTemplate} instance.
     * 
     * @param  tokens
     *         List of Tokens to convert.
     * @param  collector
     *         {@link ParseWarnCollector} instance to use.
     * 
     * @return ExpressionTemplate instance created from the List of Tokens.
     */
    public ExpressionTemplate parse(List<Token> tokens, ParseWarnCollector collector){
        CheckUtil.notNullOrEmpty(tokens, ExpressionTemplateParser.class, "Tokens");
        CheckUtil.notNull(collector, ExpressionTemplateParser.class, "Collector");
        
        List<ExpressionTemplate> parts = new ArrayList<>();
        List<Operator> operators = new ArrayList<>();
        
        try{
            parts.add(read(tokens, collector));
        }catch(IllegalArgumentException ex){
            collector.appendWarning(ex.getMessage());
            return null;
        }
        
        while(!tokens.isEmpty()){
            Token token = tokens.remove(0);
            Operator operator = this.operators.get(token);
            if(operator == null){
                collector.appendWarningFormatted("Received \"%s\" but expected OPERATOR.", token);
                return null;
            }
            
            operators.add(operator);
            if(tokens.isEmpty()){
                collector.appendWarning("Received unexpected end of input.");
                return null;
            }
            
            try{
                parts.add(read(tokens, collector));
            }catch(IllegalArgumentException ex){
                collector.appendWarning(ex.getMessage());
                return null;
            }
        }
        
        while(!operators.isEmpty()){
            Operator operator = operators.get(0);
            int lowest = operator.getPriority();
            int start = 0;
            int end = 1;
            for(int i = 1; i < operators.size(); i++){
                operator = operators.get(i);
                if(operator.getPriority() < lowest){
                    lowest = operator.getPriority();
                    start = i;
                    end = i + 1;
                }else
                if(operator.getPriority() > lowest){
                    break;
                }else{
                    end++;
                }
            }
            
            operator = operators.get(start);
            
            ExpressionTemplate replacement;
            if(start + 1 == end){
                replacement = operator.createTemplate(parts.get(start), parts.get(end));
            }else
            if(operator instanceof ListOperator listOperator){
                replacement = listOperator.createTemplate(new ArrayList<>(parts.subList(start, end + 1)));
            }else{
                List<ExpressionTemplate> conditions = new ArrayList<>(end - start);
                for(int i = start; i < end; i++)
                    conditions.add(operators.get(i).createTemplate(parts.get(i), parts.get(i + 1)));
                
                replacement = ExpressionTemplates.and(conditions);
            }
            
            for(int i = start; i < end; i++){
                parts.remove(start);
                operators.remove(start);
            }
            
            parts.set(start, replacement);
        }
        
        return parts.get(0);
    }
    
    private ExpressionTemplate read(List<Token> tokens, ParseWarnCollector collector){
        for(ValueReader valueReader : valueReaders){
            ExpressionTemplate template = valueReader.read(this, tokens, collector);
            if(template != null)
                return template;
        }
        
        throw new IllegalArgumentException("Invalid Expression. Expected literal but got token \"" + tokens.get(0).toString() + "\".");
    }
}
