# ExpressionParser

ExpressionParser is a copy of the original expression parser made by [@CodeCrafter47][CodeCrafter47] for the [TabOverlayCommon] project used by his plugins [BungeeTabListPlus] and [AdvancedTabOverlay] and shared with his permission.

It allows you to parse simple to complex expressions using Tokenization, while also allowing you to further extend and customize it.

[CodeCrafter47]: https://github.com/CodeCrafter47/
[TabOverlayCommon]: https://github.com/CodeCrafter47/TabOverlayCommon/
[BungeeTabListPlus]: https://github.com/CodeCrafter47/BungeeTabListPlus
[AdvancedTabOverlay]: https://github.com/CodeCrafter47/AdvancedTabOverlay

## License

This project is licensed under [GNU General Public License v3.0](./LICENSE) and as such is freely available for everyone.  
Original credit and copyright goes to CodeCrafter47 for the original Expression parser system.

## How it works

To parse a String into a [ExpressionTemplate] one has to create new instances of the [ExpressionTokenizer] and [ExpressionTemplateParser], and call their respective `parse` method.  
The [ExpressionTokenizer] takes a Iterable of [TokenReaders][TokenReader] while the [ExpressionTemplateParser] takes a ImmutableMap of [Token] keys with [Operator] values.

For the sake of demonstration are we using [DefaultExpressionParserEngine], which already creates the necessary instances for us through the provided Lists and Map.  
When calling the `compile(String, ParseWarnCollector)` method will the [DefaultExpressionParserEngine] call the `parse(String, ParseWarnCollector)` method of [ExpressionTokenizer] and `parse(List<Token>, ParseWarnCollector)` of the [ExpressionTemplateParser].

The [ExpressionTokenizer] will iterate through the String one character at a time, skipping whitespaces in the process.  
For each Iteration is it going through a list of [TokenReaders][TokenReader] to see if any returns a non-null [Token]. Should one be found will it be added to the List of Tokens the [ExpressionTokenizer] returns. The [TokenReader] returning a valid token also updates the position in the String for the next iteration.

The List of Tokens is being given to the [ExpressionTemplateParser] which first will try to turn as many of the tokens into a single [ExpressionTemplate].  
It does so by iterating through a list of [ValueReaders][ValueReader], giving each the list of [Tokens][Token] to try and convert. Should no valid [ValueReader] be found will an exception be thrown, which is caught by [ExpressionTemplateParser], added to the [ParseWarnCollector] before returning `null` to cancel the parsing.  
Should, however, a valid [ExpressionTemplate] be found, will it be added to a list before moving on to finding Operators.

To find Operators, the [ExpressionTemplateParser] first checks if the list of [Tokens][Token] still has entries left. Should this be the case will the first entry be removed from the list, which also gives the [Token] entry that was removed.  
This [Token] is then used as a key for the ImmutableMap containing [Token] keys and [Operator] values. Should no entry be found will a warning be added to the [ParseWarnCollector] before `null` is returned to stop the parsing.  
Should a [Operator] be found will it be added to a list of Operators before continuing with parsing the remaining tokens the same way like in the start. Should the list at this point be empty is a warning added to the [ParseWarnCollector] before `null` is returned to stop the parsing.  
In the next step is the list of [Operators][Operator] iterated through, prioritizing Operators with a higher priority. The Operator is used to create a new [ExpressionTemplate] using the two ExpressionTemplates that exist before and after the operator in the String. In the case of a [ListOperator] are the different ExpressionTemplates created by the Operators AND-ed together.

As a final step is the List of [ExpressionTemplates][ExpressionTemplate] updated before returning the very first entry of the list.

[ExpressionTemplate]: ./src/main/java/ch/andre601/expressionparser/templates/ExpressionTemplate.java
[ExpressionTokenizer]: ./src/main/java/ch/andre601/expressionparser/expressions/ExpressionTokenizer.java
[ExpressionTemplateParser]: ./src/main/java/ch/andre601/expressionparser/parsers/ExpressionTemplateParser.java
[TokenReader]: ./src/main/java/ch/andre601/expressionparser/tokens/readers/TokenReader.java
[Token]: ./src/main/java/ch/andre601/expressionparser/tokens/Token.java
[Operator]: ./src/main/java/ch/andre601/expressionparser/operator/Operator.java
[DefaultExpressionParserEngine]: ./src/main/java/ch/andre601/expressionparser/DefaultExpressionParserEngine.java
[ValueReader]: ./src/main/java/ch/andre601/expressionparser/parsers/ValueReader.java
[ParseWarnCollector]: ./src/main/java/ch/andre601/expressionparser/ParseWarnCollector.java
[ListOperator]: ./src/main/java/ch/andre601/expressionparser/operator/ListOperator.java

## Getting the library

> [!NOTE]
> Replace `{VERSION}` with the latest available release tag in this repository
> 
> ![GitHub Tag](https://img.shields.io/github/v/tag/Andre601/ExpressionParser?style=flat-square&label=Latest%20Release)

### Maven (pom.xml)

```xml
<repositories>
  <repository>
    <id>jitpack</id>
    <url>https://jitpack.io/</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>ch.andre601</groupId>
    <artifactId>ExpressionParser</artifactId>
    <version>{VERSION}</version>
    <scope>compile</scope> <!-- Includes the project -->
  </dependency>
</dependencies>
```

### Gradle (build.gradle)

```groovy
repositories {
  maven { url = "https://jitpack.io/" }
}

dependencies {
  implementation "ch.andre601:ExpressionParser:{VERSION}"
}
```

## Adding own Operands

This library allows you to create your own Operands to use in expressions, which will be explained here.  
For our example will we try to implement the placeholder syntax `${placeholder}` which will be replaced using certain values depending on the placeholder.

To get started, we first have to create a new class extending the [Token] class and add the necessary constructor:

```java
import ch.andre601.expressionparser.tokens.Token;

public class PlaceholderToken extends Token{
    
    // This will later hold our parsed placeholder value.
    private final Placeholder value;
    
    public PlaceholderToken(Placeholder value){
        super("PLACEHOLDER");
        this.value = value;
    }
    
    public Placeholder getValue(){
        return value;
    }
}
```
Make sure that the String you provide to the `super` is unique, as it is used as the Token's ID in errors to identify it.

Next step is to create a [TokenReader] for the PlaceholderToken, so that the [ExpressionTokenizer] can actually recognize our placeholder and return the right token for it.  
Simply create a new class that extends the [TokenReader] and add the necessary constructor and method override. In our case would it look like this:

```java
import ch.andre601.expressionparser.ParseWarnCollector;
import ch.andre601.expressionparser.tokens.Token;
import ch.andre601.expressionparser.tokens.readers.TokenReader;

import java.text.ParsePosition;

public class PlaceholderTokenReader extends TokenReader{
    
    public PlaceholderTokenReader(int priority){
        super(priority);
    }
    
    @Override
    public Token read(String text, ParsePosition position, ParseWarnCollector collector){
        if(position.getIndex() + 1 < text.length() && text.charAt(position.getIndex()) == '$' && text.charAt(position.getIndex() + 1) == '{'){
            position.setIndex(position.getIndex() + 2);
            return new PlaceholderToken(PlaceholderParser.parse(text, position, collector));
        }
        
        return null;
    }
}
```
In the `read` method are we doing a few things:

1. We check if the current position in the text + 1 is less than the text's total length.
2. We check if the character at the current position in the text equals `$`.
3. We check if the character at the next position in the text equals `{`.
4. In case of all of the above being true are we increasing the text position by 2 and return a new PlaceholderToken instance.

In our example do we use a separate class - the PlaceholderParser - to parse the String into a Placeholder instance.

```java
import ch.andre601.expressionparser.ParseWarnCollector;

import java.text.ParsePosition;

public class PlaceholderParser{
    
    public static Placeholder parse(String text, ParsePosition position, ParseWarnCollector collector){
        int start = position.getIndex();
        int index = position.getIndex();
        
        StringBuilder values = new StringBuilder();
        
        boolean invalid = true;
        boolean hadSpace = false;
        
        // We only need the text that has been identified as start of a placeholder.
        char[] chars = text.substring(index).toCharArray();
        
        // This loop just goes through the text, considerin a placeholder valid if it finds a } before any space
        for(final char c : chars){
            index++;
            
            if(c == ' '){
                hadSpace = true;
                break;
            }
            
            if(c == '}'){
                invalid = false;
                break;
            }
            
            values.append(c);
        }
        
        String valueStr = values.toString();
        
        // Reset StringBuilder
        values.setLength(0);
        
        // Set to the last position in the for loop + 1 to skip the last found character (Space or })
        position.setIndex(index + 1);
        
        if(invalid){
            values.append("${")
                .append(valueStr);
            
            if(hadSpace){
                collector.appendWarning(start, "Placeholder contained space character.");
                values.append(' ');
            }else{
                collector.appendWarning(start, "Placeholder does not have any closing bracket.");
            }
            
            return values.toString();
        }
        
        return switch(valueStr){
            case "placeholder1" -> new Placeholder("1");
            case "placeholder2" -> new Placeholder("2");
            case "placeholder3" -> new Placeholder("3");
            default -> null;
        };
    }
}
```

Now the question: What is `Placeholder` actually?  
It's a class that implements the [ExpressionTemplate] interface to return [ToBooleanExpression], [ToDoubleExpression] and [ToStringExpressions] for the ExpressionEngine to use.

Here is how it looks:
```java
import ch.andre601.expressionparser.expressions.ToBooleanExpression;
import ch.andre601.expressionparser.expressions.ToDoubleExpression;
import ch.andre601.expressionparser.expressions.ToStringExpression;
import ch.andre601.expressionparser.templates.ExpressionTemplate;

public class Placeholder implements ExpressionTemplate{
    
    private final ToBooleanExpression toBooleanExpression;
    private final ToDoubleExpression toDoubleExpression;
    private final ToStringExpression toStringExpression;
    
    public Placeholder(String value){
        double doubleValue;
        try{
            doubleValue = Double.parseDouble(value);
        }catch(NumberFormatException ex){
            doubleValue = value.length();
        }
        
        this.toBooleanExpression = ToBooleanExpression.literal(Boolean.parseBoolean(value));
        this.toDoubleExpression = ToDoubleExpression.literal(doubleValue);
        this.toStringExpression = ToStringExpression.literal(value);
    }
    
    @Override
    public ToBooleanExpression returnBooleanExpression(){
        return toBooleanExpression;
    }
    
    @Override
    public ToDoubleExpression returnDoubleExpression(){
        return toDoubleExpression;
    }
    
    @Override
    public ToStringExpression returnStringExpression(){
        return toStringExpression;
    }
}
```
What we did here is create a class that accepts a String in its constructor and creates a [ToBooleanExpression], [ToDoubleExpression] and [ToStringExpression] instance to return when used.  
For the ToDoubleExpression do we try to parse the String as a double and should it fail, use the length of the String itself.

It's worth pointing out that the library offers a [ConstantExpressionTemplate] which you could use instead of making a Placeholder class, as it already has the same functionality available, allowing you to create instances using available static methods for boolean, double and String.

The next step to take now is to create a class extending the abstract [ValueReader] class and override the `read` method:

```java
import ch.andre601.expressionparser.ParseWarnCollector;
import ch.andre601.expressionparser.parsers.ExpressionTemplateParser;
import ch.andre601.expressionparser.parsers.ValueReader;
import ch.andre601.expressionparser.templates.ExpressionTemplate;
import ch.andre601.expressionparser.tokens.Token;

import java.util.List;

public class PlaceholderReader extends ValueReader{
    
    @Override
    public ExpressionTemplate read(ExpressionTemplateParser parser, List<Token> tokens, ParseWarnCollector collector){
        if(tokens.get(0) instanceof PlaceholderToken){
            PlaceholderToken token = (PlaceholderToken)tokens.remove(0);
            return token.getValue();
        }
        
        return null;
    }
}
```
This would now check if the first token in the list is a instance of PlaceholderToken and if true, gets it from the list while also removing it before returning its value, which would be our Placeholder class.  
Should it not be such a token will null be returned instead.

Now as a final step do we need to add the [Token], [TokenReader] and [ValueReader] into the [ExpressionTemplateParser] or [ExpressionTokenizer], depending on what it is.  
We will use the [DefaultExpressionParserEngine] as it offers a Builder class to more easily add the necessary instances to use. Here is an example again:

```java
import ch.andre601.expressionparser.DefaultExpressionParserEngine;
import ch.andre601.expressionparser.ParseWarnCollector;
import ch.andre601.expressionparser.templates.ExpressionTemplate;

public class ConditionParser{
    
    private final DefaultExpressionParserEngine engine;
    
    public ConditionParser(){
        engine = new DefaultExpressionParserEngine.DefaultBuilder().createDefault()
            .addTokenReader(new PlaceholderTokenReader(-20))
            .addValueReader(new PlaceholderReader())
            .build();
    }
    
    public boolean parse(String text){
        ParseWarnCollector collector = new ParseWarnCollector(text);
        ExpressionTemplate result = engine.compile(text, collector);
        
        if(collector.hasWarnings()){
            System.out.println("Encountered issues while parsing expression '" + text + "':");
            
            for(ParseWarnCollector.Context context : collector.getWarnings()){
                if(context.position() == -1){
                    System.out.println("  - " + context.message());
                }else{
                    System.out.println("  - At position " + context.position() + ": " + context.message());
                }
            }
        }
        
        if(result == null)
            return false;
        
        return result.returnBooleanExpression().evaluate();
    }
}
```

In this final example are we doing a lot of things.  
First of all are we creating a new [DefaultExpressionParserEngine] using the Builder's `createDefault()` static method. This gives us an instance of the Builder with default [TokenReaders][TokenReader], [Operators][Operator] and [ValueReaders][ValueReader] already applied.  
When then add our own [TokenReader] and [ValueReader] to the Builder before building it, creating the [DefaultExpressionParserEngine].

Finally are we using this engine instance in a `parse` method, creating a [ParseWarnCollector] too.  
We simply call the engine's `compile` method to retrieve a [ExpressionTemplate] instance. At this point will we check the collector for if it has received any warnings and should this be the case, print them in our console.  
Finally are we checking for the template to not be null. If it is, return false, else get the [ToBooleanExpression] instance it holds and call `evaluate()` to return the boolean value stored.

You now have a working Placeholder token parser!

[ToBooleanExpression]: ./src/main/java/ch/andre601/expressionparser/expressions/ToBooleanExpression.java
[ToDoubleExpression]: ./src/main/java/ch/andre601/expressionparser/expressions/ToDoubleExpression.java
[ToStringExpression]: ./src/main/java/ch/andre601/expressionparser/expressions/ToStringExpression.java
[ConstantExpressionTemplate]: ./src/main/java/ch/andre601/expressionparser/templates/ConstantExpressionTemplate.java
