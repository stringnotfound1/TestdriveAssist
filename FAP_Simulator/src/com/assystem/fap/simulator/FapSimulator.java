package com.assystem.fap.simulator;

import java.lang.reflect.Field;
import java.net.URL;

import com.assystem.fap.simulator.ui.mainframe.MainFrame;
import com.berner.mattner.javafx.JavaFxLauncher;
import com.berner.mattner.tools.networking.server.Server;

import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

@Data
public class FapSimulator {

	public static final String CSS_PATH = "/com/assystem/fap/simulator/BM.css";
	public static final URL CSS_URL = FapSimulator.class.getResource(CSS_PATH);
	public static final String CONNECTION_END_TOKEN = "closing";

	private final Server server = new Server();

	public static void main(String[] args) {
		new FapSimulator().start();
	}

	public void start() {
		JavaFxLauncher.startJavaFxThread();
		setupServer();
		new MainFrame(this::onFieldChanged, this::onClose, new ValueDatabase()).start();
	}

	@SneakyThrows
	public void setupServer() {
		server.setOnInput((client, length, bytes) -> System.out.println(client + " sent: " + new String(bytes)));
		server.setOnConnectionEstablished(socket -> System.out.println(socket + " connected"));
		server.start(60000);
	}

	public void onFieldChanged(final @NonNull Field field, final Object value) {
		server.writeBroadcast((field.getName() + ":" + value).getBytes());
	}

	public void onClose() {
		server.writeBroadcast(CONNECTION_END_TOKEN.getBytes());
		server.close();
	}

	@Data
	public class ValueDatabase {
		private float speed;
		private float steeringAngle;
		private boolean lights;
		private int numDetectedPersons;
	}
}