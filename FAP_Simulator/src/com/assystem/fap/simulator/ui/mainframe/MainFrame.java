package com.assystem.fap.simulator.ui.mainframe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.assystem.fap.simulator.FapSimulator;
import com.berner.mattner.javafx.JavaFxApplicationWrapper;
import com.berner.mattner.javafx.JavaFxUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class MainFrame extends JavaFxApplicationWrapper implements Initializable {

	private @FXML GridPane gp_items;
	private final BiConsumer<Field, Object> onFieldChaned;
	private final Runnable onClose;
	private final Object valueHolder;

	@Override
	public void start(final @NonNull Stage primaryStage) throws Exception {
		primaryStage.setScene(loadFxmlWithCustomController(getClass().getResource("MainFrame.fxml"),
				getClass().getResource(FapSimulator.CSS_PATH), this));
		primaryStage.setOnCloseRequest(e -> onClose.run());
		primaryStage.setTitle("FAP - Input Simulator");
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Field> valueFields = Arrays.stream(valueHolder.getClass().getDeclaredFields())
				.filter(field -> !Modifier.isStatic(field.getModifiers())).collect(Collectors.toList());
		IntStream.range(0, valueFields.size()).forEach(i -> {
			Field field = valueFields.get(i);
			Node node = JavaFxUtils.createUiNodeForType(field.getType(), null, (obs, oldVal, newVal) -> {
				try {
					field.setAccessible(true);
					field.set(valueHolder, newVal);
					onFieldChaned.accept(field, newVal);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
			if (node == null)
				return;
			gp_items.getRowConstraints().add(new RowConstraints(30));
			gp_items.add(new Label(field.getName()), 0, i);
			gp_items.add(node, 1, i);
		});
	}
}
