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

package ch.andre601.expressionparser.internal;

import java.util.Collection;
import java.util.Map;

public class CheckUtil{
    
    public static void notNullOrEmpty(Map<?, ?> map, Class<?> clazz, String name){
        notNull(map, clazz, name);
        
        if(map.isEmpty())
            throw new IllegalArgumentException("[" + clazz.getSimpleName() + "] " + name + " may not be empyt.");
    }
    
    public static void notNullOrEmpty(Collection<?> list, Class<?> clazz, String name){
        notNull(list, clazz, name);
        
        if(list.isEmpty())
            throw new IllegalArgumentException("[" + clazz.getSimpleName() + "] " + name + " may not be empty.");
    }
    
    public static void notNullOrEmpty(String str, Class<?> clazz, String name){
        notNull(str, clazz, name);
        
        if(str.isEmpty())
            throw new IllegalArgumentException("[" + clazz.getSimpleName() + "] " + name + " may not be empty.");
    }
    
    public static void noneNull(Class<?> clazz, String name, Object... objects){
        for(Object obj : objects){
            notNull(obj, clazz, name);
        }
    }
    
    public static void notNull(Object object, Class<?> clazz, String name){
        if(object == null)
            throw new IllegalArgumentException("[" + clazz.getSimpleName() + "] " + name + " may not be null.");
    }
}
