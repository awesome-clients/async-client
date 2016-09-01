package zx.soft.async.httpclient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class AsyncHttpClientSingletonTest {

	@Test
	public void test() throws InterruptedException, ExecutionException, TimeoutException, IOException {
		AsyncHttpClientSingleton singleton = AsyncHttpClientSingleton.getInstance();
		System.out.println(singleton.get("http://www.example.com/").get());
		singleton.close();
	}

	@Test
	public void testMultiThread() throws InterruptedException {
		AsyncHttpClientSingleton singleton = AsyncHttpClientSingleton.getInstance();
		for (int i = 0; i < 8; i++) {
			new Thread(new PostRunnable(singleton)).start();
		}
		TimeUnit.MINUTES.sleep(5);

	}

	private static class PostRunnable implements Runnable {

		private AsyncHttpClientSingleton client;

		public PostRunnable(AsyncHttpClientSingleton client) {
			this.client = client;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					System.out.println(this.client.get("http://www.example.com/").get());
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
