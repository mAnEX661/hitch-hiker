package autostoppista.core.http;
public class HTTPCalls{
	public static void start(HTTPInput i) {
		HTTPAsync f = new HTTPAsync();
		f.execute(i);
	}
	public static void start(HTTPInput i, HTTPStartListener s) {
		HTTPAsync f = new HTTPAsync(s);
		f.execute(i);
	}
	public static void start(HTTPInput i, HTTPonCompleteListener c) {
		HTTPAsync f = new HTTPAsync(c);
		f.execute(i);
	}
	public static void start(HTTPInput i, HTTPStartListener s,HTTPonCompleteListener c) {
		HTTPAsync f = new HTTPAsync(s, c);
		f.execute(i);
	}
	public static void startBasic(HTTPInputBasic i) {
		HTTPAsyncBasic f = new HTTPAsyncBasic();
		f.execute(i);
	}
	public static void startBasic(HTTPInputBasic i, HTTPStartListener s) {
		HTTPAsyncBasic f = new HTTPAsyncBasic(s);
		f.execute(i);
	}
	public static void startBasic(HTTPInputBasic i, HTTPonCompleteListener c) {
		HTTPAsyncBasic f = new HTTPAsyncBasic(c);
		f.execute(i);
	}
	public static void startBasic(HTTPInputBasic i, HTTPStartListener s,HTTPonCompleteListener c) {
		HTTPAsyncBasic f = new HTTPAsyncBasic(s, c);
		f.execute(i);
	}
}