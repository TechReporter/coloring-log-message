package techreporter.logging;

import java.util.StringTokenizer;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class CustomLoggingPatternLayout extends PatternLayout {

	public CustomLoggingPatternLayout() {
		super(DEFAULT_CONVERSION_PATTERN);
	}

	public CustomLoggingPatternLayout(String pattern) {
		super(pattern);
	}

	private static final String CONFIGURATION_SEPARATOR = "/";

	private static final String PREFIX = "\u001b[";
	private static final String SUFFIX = "m";
	private static final char SEPARATOR = ';';
	private static final String END_COLOR = PREFIX + SUFFIX;

	//text brightness
	private static final String ATTR_NORMAL = "0";
	private static final String ATTR_BRIGHT = "1";
	private static final String ATTR_DIM = "2";
	private static final String ATTR_UNDERLINE = "3";
	private static final String ATTR_BLINK = "5";
	private static final String ATTR_REVERSE = "7";
	private static final String ATTR_HIDDEN = "8";

	// foreground color
	private static final String FG_BLACK = "30";
	private static final String FG_RED = "31";
	private static final String FG_GREEN = "32";
	private static final String FG_YELLOW = "33";
	private static final String FG_BLUE = "34";
	private static final String FG_MAGENTA = "35";
	private static final String FG_CYAN = "36";
	private static final String FG_WHITE = "37";

	//background color
	private static final String BG_BLACK = "40";
	private static final String BG_RED = "41";
	private static final String BG_GREEN = "42";
	private static final String BG_YELLOW = "43";
	private static final String BG_BLUE = "44";
	private static final String BG_MAGENTA = "45";
	private static final String BG_CYAN = "46";
	private static final String BG_WHITE = "47";

	private String fatalLogColor = PREFIX.concat(ATTR_DIM) + SEPARATOR +FG_RED + SUFFIX;
	private String errorLogColor = PREFIX.concat(ATTR_DIM) + SEPARATOR +FG_RED + SUFFIX;
	private String warnLogColor = PREFIX.concat(ATTR_DIM) + SEPARATOR +FG_YELLOW + SUFFIX;
	private String debugLogColor = PREFIX.concat(ATTR_DIM) + SEPARATOR +FG_CYAN + SUFFIX;
	private String traceLogColor = PREFIX.concat(ATTR_DIM) + SEPARATOR +FG_MAGENTA + SUFFIX;
	private String infoLogColor = PREFIX.concat(ATTR_DIM) + SEPARATOR +FG_GREEN + SUFFIX;


	public String format(LoggingEvent event) {
		if (event.getLevel() == Level.FATAL) {
			return fatalLogColor + super.format(event) + END_COLOR;
		} else if (event.getLevel() == Level.ERROR) {
			return errorLogColor + super.format(event) + END_COLOR;
		}
		if (event.getLevel() == Level.WARN) {
			return warnLogColor + super.format(event) + END_COLOR;
		}
		if (event.getLevel() == Level.INFO) {
			return infoLogColor + super.format(event) + END_COLOR;
		}
		if (event.getLevel() == Level.DEBUG) {
			return debugLogColor + super.format(event) + END_COLOR;
		}
		if (event.getLevel() == Level.TRACE) {
			return traceLogColor + super.format(event) + END_COLOR;
		}
		else {
			throw new RuntimeException("Unsupported Level " + event.toString());
		}
	}
	
	
	private String makeFgString(String fgColorName) {
        System.out.println("fg " + fgColorName);
        if (fgColorName.toLowerCase().equals("black")) {
            return FG_BLACK;
        } else if (fgColorName.toLowerCase().equals("red")) {
            return FG_RED;
        } else if (fgColorName.toLowerCase().equals("green")) {
            return FG_GREEN;
        } else if (fgColorName.toLowerCase().equals("yellow")) {
            return FG_YELLOW;
        } else if (fgColorName.toLowerCase().equals("blue")) {
            return FG_BLUE;
        } else if (fgColorName.toLowerCase().equals("magenta")) {
            return FG_MAGENTA;
        } else if (fgColorName.toLowerCase().equals("cyan")) {
            return FG_CYAN;
        } else if (fgColorName.toLowerCase().equals("white")) {
            return FG_WHITE;
        }
        System.out.println("log4j: ColoredPatternLayout Unsupported FgColor " + fgColorName);
        return "-1";
    }

	
    private String makeBgString(String bgColorName) {
        if (bgColorName.toLowerCase().equals("black")) {
            return BG_BLACK;
        } else if (bgColorName.equalsIgnoreCase("red")) {
            return BG_RED;
        } else if (bgColorName.equalsIgnoreCase("green")) {
            return BG_GREEN;
        } else if (bgColorName.equalsIgnoreCase("yellow")) {
            return BG_YELLOW;
        } else if (bgColorName.equalsIgnoreCase("blue")) {
            return BG_BLUE;
        } else if (bgColorName.equalsIgnoreCase("magenta")) {
            return BG_MAGENTA;
        } else if (bgColorName.equalsIgnoreCase("cyan")) {
            return BG_CYAN;
        } else if (bgColorName.equalsIgnoreCase("white")) {
            return BG_WHITE;
        }
        System.out.println("log4j: ColoredPatternLayout Unsupported BgColor " + bgColorName);
        return "-1";
    }
	
	
    private String makeColorString(String colorName) {
        System.out.println(colorName);
        StringTokenizer tokenizer = new StringTokenizer(colorName,
                CONFIGURATION_SEPARATOR);
        String fgColor = FG_WHITE;
        String bgColor = BG_BLACK;
        String attr = ATTR_NORMAL;
        if (!tokenizer.hasMoreTokens()) {
            return PREFIX + ATTR_NORMAL + SEPARATOR + FG_WHITE + SUFFIX;
        }
        if (tokenizer.hasMoreTokens()) {
            fgColor = makeFgString(tokenizer.nextToken());
        }
        if (tokenizer.hasMoreTokens()) {
            bgColor = makeBgString(tokenizer.nextToken());
        }
        if (tokenizer.hasMoreTokens()) {
            attr = makeAttributeString(tokenizer.nextToken());
        }
        //return PREFIX + ATTR_DIM + SEPARATOR + FG_WHITE + SUFFIX;
        return PREFIX + attr
                + SEPARATOR + fgColor
                + SEPARATOR + bgColor
                + SUFFIX;
    }
    
    private String makeAttributeString(String attributeName) {
        System.out.println("attribute " + attributeName);
        if (attributeName.toLowerCase().equals("normal")) {
            return ATTR_NORMAL;
        } else if (attributeName.toLowerCase().equals("bright")) {
            return ATTR_BRIGHT;
        } else if (attributeName.toLowerCase().equals("dim")) {
            return ATTR_DIM;
        } else if (attributeName.toLowerCase().equals("underline")) {
            return ATTR_UNDERLINE;
        } else if (attributeName.toLowerCase().equals("blink")) {
            return ATTR_BLINK;
        } else if (attributeName.toLowerCase().equals("reverse")) {
            return ATTR_REVERSE;
        } else if (attributeName.toLowerCase().equals("hidden")) {
            return ATTR_HIDDEN;
        }
        System.out.println("log4j: ColoredPatternLayout Unsupported Attribute " + attributeName);

        return "-1";
    }

	public String getFatalLogColor() {
		return fatalLogColor;
	}

	public void setFatalLogColor(String fatalLogColor) {
		this.fatalLogColor = makeColorString(fatalLogColor);
	}

	public String getErrorLogColor() {
		return errorLogColor;
	}

	public void setErrorLogColor(String errorLogColor) {
		this.errorLogColor = makeColorString(errorLogColor);
	}

	public String getWarnLogColor() {
		return warnLogColor;
	}

	public void setWarnLogColor(String warnLogColor) {
		this.warnLogColor = makeColorString(warnLogColor);
	}

	public String getDebugLogColor() {
		return debugLogColor;
	}

	public void setDebugLogColor(String debugLogColor) {
		this.debugLogColor = makeColorString(debugLogColor);
	}

	public String getTraceLogColor() {
		return traceLogColor;
	}

	public void setTraceLogColor(String traceLogColor) {
		this.traceLogColor = makeColorString(traceLogColor);
	}

	public String getInfoLogColor() {
		return infoLogColor;
	}

	public void setInfoLogColor(String infoLogColor) {
		this.infoLogColor = makeColorString(infoLogColor);
	}
    
    

}
