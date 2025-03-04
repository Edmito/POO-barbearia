package com.barbershop.views.calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Event;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * The CalendarController class implements the Initializable interface to initialize the calendar view and functionalities.
 * The CalendarController class manages the calendar view.
 * It allows users to view appointments, navigate through months, and search for specific events.
 */
public class Calendar implements Initializable {
    // date of the selected events
    private LocalDateTime dateFocus;
    public static LocalDate selected_date;
    private LocalDateTime today;
    // current selected rectangle
    private Rectangle currentClickedRectangle = null;
    //current selected event
    public static Event selectedEvent;
    //list of all the events mapped to the calendar
    public static Map<Integer, List<Event>> calendareventsMap = null;

    // FXML elements injected from the FXML file
    // calendar title
    @FXML
    private Text year;
    @FXML
    private Text month;
    @FXML
    private FlowPane calendar;

    //back to the previous month button
    @FXML
    private Button backOneMonth;

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        selectedEvent = null;
        selected_date = null;
        initButtonStyle();
        drawCalendar();
    }

    //go to next month button
    @FXML
    private Button forwardOneMonth;

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        selectedEvent = null;
        selected_date = null;
        initButtonStyle();
        drawCalendar();
    }

    //list of selected events
    @FXML
    private ListView<Event> selectionList;

    private void addEventList(Event event) {
        selectionList.getItems().add(event);
        selectedEvent = null;
        selected_date = null;
        initButtonStyle();
    }

    //search from the event list
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;

    @FXML
    void onActionSearchButton(ActionEvent event) {
        search();
        selectedEvent = null;
        selected_date = null;
        initButtonStyle();
    }

    private void search() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        List<Event> searchResults = new ArrayList<>();

        // Iterate through all events in the calendareventsMap
        for (List<Event> events : calendareventsMap.values()) {
            for (Event eve : events) {
                // Convert attributes to lowercase for case-insensitive comparison
                String clientName = eve.getClient().getFirst_name().toLowerCase() + " " + eve.getClient().getLast_name().toLowerCase();
                String date = eve.getDateAndTime().toLowerCase();
                String description = eve.getDecription().toLowerCase();
                String service = eve.getService().getName().toLowerCase();

                // Check if any attribute contains the search term
                if (clientName.contains(searchTerm) ||
                        date.contains(searchTerm) ||
                        description.contains(searchTerm) ||
                        service.contains(searchTerm)) {
                    // If the eve matches the search term, add it to the search results
                    searchResults.add(eve);
                }
            }
        }

        // Clear the current items in the selectionList
        selectionList.getItems().clear();

        // Display search results in the selectionList
        for (Event result : searchResults) {
            addEventList(result);
        }
    }

    //return to the current day
    @FXML
    private Button backButton;

    @FXML
    void onActionBackButton(ActionEvent event) {
        dateFocus = LocalDateTime.now();
        today = LocalDateTime.now();
        calendar.getChildren().clear();
        selectedEvent = null;
        selected_date = null;
        initButtonStyle();
        drawCalendar();
    }

    //add a new event to the calendar button 
    @FXML
    private Button addButton;

    @FXML
    void onActionAddButton(ActionEvent event) {
        addEvent();
    }

    private void addEvent() {
        //Open a wizard to add an event
        AddEvent.openAddEventWizard();
        //refresh the calendar
        calendar.getChildren().clear();
        selectionList.getItems().clear();
        selectedEvent = null;
        selected_date = null;
        initButtonStyle();
        drawCalendar();
    }

    //add invoice to an event button
    @FXML
    private Button invoiceButton;

    @FXML
    void onActionInvoiceButton(ActionEvent event) {
        invoice();
    }

    private void invoice() {
        if (selectedEvent != null) {
            if (selectedEvent.getInvoice() == null || selectedEvent.getInvoiceId() == 0) {
                GenerateInvoice.openGenerateInvoiceWizard();
                //refresh the calendar
                calendar.getChildren().clear();
                selectionList.getItems().clear();
                selectedEvent = null;
                selected_date = null;
                initButtonStyle();
                drawCalendar();
            } else {
                SeeInvoice.openSeeInvoiceWizard();
            }
        }
    }

    @FXML
    private Button editButton;

    @FXML
    void onActionEditButton(ActionEvent event) {
        edit();
    }

    private void edit() {
        if (selectedEvent != null) {
            //Open a wizard to add an event
            EditEvent.openEditEventWizard();
            //refresh the calendar
            calendar.getChildren().clear();
            selectionList.getItems().clear();
            selectedEvent = null;
            selected_date = null;
            initButtonStyle();
            drawCalendar();
        }
    }

    @FXML
    private Button deleteButton;

    @FXML
    void onActionDeleteButton(ActionEvent event) {
        if (selectedEvent != null) {
            //Open a wizard to add an event
            DeleteEvent.openDeleteEventWizard();
            //refresh the calendar
            calendar.getChildren().clear();
            selectionList.getItems().clear();
            selectedEvent = null;
            selected_date = null;
            initButtonStyle();
            drawCalendar();
        }
    }

    private void initButtonStyle() {
        if (selectedEvent == null) {
            HoverController.setMenuButtonStyle(invoiceButton, Colors.secondary);
            HoverController.setMenuButtonStyle(editButton, Colors.secondary);
            HoverController.setMenuButtonStyle(deleteButton, Colors.secondary);
            HoverController.noMenuHoverEffect(invoiceButton, Colors.secondary);
            HoverController.noMenuHoverEffect(editButton, Colors.secondary);
            HoverController.noMenuHoverEffect(deleteButton, Colors.secondary);
        } else {
            HoverController.setMenuButtonStyle(invoiceButton, Colors.forground);
            HoverController.setMenuButtonStyle(editButton, Colors.forground);
            HoverController.setMenuButtonStyle(deleteButton, Colors.forground);
            HoverController.addMenuHoverEffect(invoiceButton, Colors.secondary);
            HoverController.addMenuHoverEffect(editButton, Colors.secondary);
            HoverController.addMenuHoverEffect(deleteButton, Colors.secondary);
        }
    }

    @FXML
    private HBox topMenu;
    @FXML
    private HBox bottomMenu;
    @FXML
    private VBox rightMenu;
    @FXML
    private HBox leftMenu;
    @FXML
    private HBox calendarHead;
    @FXML
    private HBox calendarHead2;

    //innitialize the calendar
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Configurações iniciais
        topMenu.setStyle("-fx-background-color: " + Colors.primary + "; -fx-padding: 10");
        bottomMenu.setStyle("-fx-background-color: " + Colors.primary + "; -fx-padding: 10");
        rightMenu.setStyle("-fx-background-color: " + Colors.background2);
        leftMenu.setStyle("-fx-background-color: " + Colors.background);
        selectionList.setStyle("-fx-control-inner-background: " + Colors.background2 + ";" + "-fx-background-color: " + Colors.background2);
        searchField.setStyle("-fx-background-color: " + Colors.secondary + "; -fx-text-fill: " + Colors.text2);
        calendarHead.setStyle("-fx-background-color: " + Colors.primary);
        calendarHead2.setStyle("-fx-background-color: " + Colors.secondary);

        // Estilos dos botões
        HoverController.setSubMenuButtonStyle(backOneMonth, Colors.forground);
        HoverController.setSubMenuButtonStyle(forwardOneMonth, Colors.forground);
        HoverController.setMenuButtonStyle(searchButton, Colors.forground);
        HoverController.setMenuButtonStyle(backButton, Colors.forground);
        HoverController.setMenuButtonStyle(addButton, Colors.forground);
        HoverController.addSubMenuHoverEffect(backOneMonth, Colors.secondary);
        HoverController.addSubMenuHoverEffect(forwardOneMonth, Colors.secondary);
        HoverController.addMenuHoverEffect(searchButton, Colors.secondary);
        HoverController.addMenuHoverEffect(backButton, Colors.secondary);
        HoverController.addMenuHoverEffect(addButton, Colors.secondary);
        initButtonStyle();

        // Inicializar o mês e o ano
        dateFocus = LocalDateTime.now();
        today = LocalDateTime.now();

        // Exibir o ano e o mês em português
        year.setText(String.valueOf(dateFocus.getYear()));
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        month.setText(dateFocus.format(monthFormatter));

        // Estilo do título do calendário
        year.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        year.setFill(Color.WHITESMOKE);
        month.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        month.setFill(Color.WHITESMOKE);
        calendar.setHgap(-1);
        calendar.setVgap(-1);

        // Evento de clique na lista de seleção
        selectionList.setOnMouseClicked(event -> {
            selectedEvent = selectionList.getSelectionModel().getSelectedItem();
            initButtonStyle();
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (selectedEvent != null) {
                    edit();
                }
            }
        });

        // Evento de tecla pressionada no campo de pesquisa
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        // Desenhar o calendário
        drawCalendar();
    }

    private void initCalendar() {
        //get data from database
        GetData.GetAll();
        //map the data to the calendar
        calendareventsMap = getCalendarEventscalendarEventsMonth(dateFocus);
    }

    //fetch events from the database
    private Map<Integer, List<Event>> getCalendarEventscalendarEventsMonth(LocalDateTime dateFocus) {
        List<Event> calendarEvents = new ArrayList<>();
        for (Event event : GetData.AllEvents) {
            calendarEvents.add(event);
        }
        return createCalendarMap(calendarEvents);
    }

    //Maping the events to a list sorted by time
    private Map<Integer, List<Event>> createCalendarMap(List<Event> calendarEvents) {
        Map<Integer, List<Event>> calendareventsMap = new HashMap<>();

        for (Event event : calendarEvents) {
            int eventDate = event.getDateTime().getDayOfMonth();
            if (!calendareventsMap.containsKey(eventDate)) {
                calendareventsMap.put(eventDate, new ArrayList<>());
            }
            calendareventsMap.get(eventDate).add(event);
        }

        // Sort events by time
        for (List<Event> events : calendareventsMap.values()) {
            events.sort(Comparator.comparing(event -> event.getDateTime()));
        }

        return calendareventsMap;
    }

    //Create the calendar view
    private void drawCalendar() {
        initCalendar();
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        //set the style for the calendar view
        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;

        int monthMaxDate = dateFocus.getMonth().maxLength();
        // Check for leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }

        int dateOffset = LocalDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                if (Colors.isDark) {
                    rectangle.setFill(Color.rgb(36, 36, 36));
                    rectangle.setStroke(Color.rgb(74, 74, 74));
                } else {
                    rectangle.setFill(Color.WHITESMOKE);
                    rectangle.setStroke(Color.rgb(230, 230, 230));
                }
                rectangle.setStrokeWidth(strokeWidth);
                // Adjust rectangle width to occupy one-seventh of the FlowPane width
                double rectangleWidth = calendarWidth / 7 - 5;
                rectangle.setWidth(rectangleWidth);

                double rectangleHeight = (calendarHeight / 6) - strokeWidth;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                int currentDate = calculatedDate - dateOffset;
                Text date = new Text(String.valueOf(currentDate));
                if (calculatedDate > dateOffset) {
                    if (currentDate <= monthMaxDate) {
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        date.setTranslateX(-(rectangleWidth / 2) * 0.75);
                        date.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                        date.setFill(Color.GREY);
                        stackPane.getChildren().add(date);

                        List<Event> calendarEvents = calendareventsMap.get(currentDate);
                        if (calendarEvents != null) {
                            createCalendarevent(calendarEvents, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (currentDate > monthMaxDate) {
                        rectangle.setFill(Color.TRANSPARENT);
                        rectangle.setStroke(Color.TRANSPARENT);
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setFill(Color.rgb(172, 227, 242));
                        rectangle.setStroke(Color.rgb(225, 225, 225));
                    }
                } else {
                    if (Colors.isDark) {
                        rectangle.setFill(Color.rgb(26, 26, 26));
                        rectangle.setStroke(Color.rgb(74, 74, 74));
                    } else {
                        rectangle.setFill(Color.rgb(230, 230, 230));
                        rectangle.setStroke(Color.rgb(220, 220, 220));
                    }
                }
                if (currentDate <= monthMaxDate && calculatedDate > dateOffset) {
                    rectangle.setOnMouseClicked(mouseEvent -> {

                        String current_date_selected = LocalDateTime.now().toString();
                        if (currentDate < 10 && dateFocus.getMonthValue() < 10) {
                            current_date_selected = dateFocus.getYear() + "-0" + dateFocus.getMonthValue() + "-0" + currentDate;
                        } else if (currentDate < 10 && dateFocus.getMonthValue() >= 10) {
                            current_date_selected = dateFocus.getYear() + "-" + dateFocus.getMonthValue() + "-0" + currentDate;
                        } else if (currentDate >= 10 && dateFocus.getMonthValue() < 10) {
                            current_date_selected = dateFocus.getYear() + "-0" + dateFocus.getMonthValue() + "-" + currentDate;
                        } else {
                            current_date_selected = dateFocus.getYear() + "-" + dateFocus.getMonthValue() + "-" + currentDate;
                        }
                        selected_date = LocalDate.parse(current_date_selected, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        if (mouseEvent.getClickCount() == 2 && !mouseEvent.isConsumed()) {
                            mouseEvent.consume();
                            addEvent();
                        }

                        // Check if the date in the rectangle matches the current date
                        boolean isCurrentDate = currentDate == today.getDayOfMonth() &&
                                dateFocus.getMonth() == today.getMonth() &&
                                dateFocus.getYear() == today.getYear();
                        // Store the previous clicked rectangle
                        Rectangle previousClickedRectangle = currentClickedRectangle;

                        // Change the color of the clicked rectangle based on whether it's the current date
                        if (!isCurrentDate) {
                            rectangle.setFill(Color.rgb(151, 230, 213));
                            rectangle.setStroke(Color.rgb(230, 230, 230));
                        } else {
                            // If it's the current date, set it to light pink when clicked
                            rectangle.setFill(Color.PINK);
                            rectangle.setStroke(Color.rgb(230, 230, 230));
                        }

                        // Update the currently clicked rectangle
                        if (currentClickedRectangle != null) {
                            // Reset the color of the previous clicked rectangle
                            if (previousClickedRectangle != null && previousClickedRectangle != rectangle) {
                                boolean wasPreviousClickedCurrentDate = (previousClickedRectangle.getFill() == Color.PINK);
                                if (wasPreviousClickedCurrentDate) {
                                    previousClickedRectangle.setFill(Color.rgb(172, 227, 242));
                                    previousClickedRectangle.setStroke(Color.rgb(230, 230, 230));
                                } else {
                                    if (Colors.isDark) {
                                        previousClickedRectangle.setFill(Color.rgb(36, 36, 36));
                                        previousClickedRectangle.setStroke(Color.rgb(74, 74, 74));
                                    } else {
                                        previousClickedRectangle.setFill(Color.WHITESMOKE);
                                        previousClickedRectangle.setStroke(Color.rgb(230, 230, 230));
                                    }
                                }
                            }
                        }
                        // Print the events for the specific date
                        List<Event> events = calendareventsMap.get(currentDate);
                        if (events != null) {
                            selectionList.getItems().clear();
                            for (Event event : events) {
                                if (event.getDateTime().getMonth().equals(dateFocus.getMonth())) {
                                    addEventList(event);
                                }
                            }
                        } else {
                            selectionList.getItems().clear();
                        }
                        currentClickedRectangle = rectangle;
                        selectedEvent = null;
                        initButtonStyle();
                    });
                    // Add hover effect
                    rectangle.setOnMouseEntered(e -> {
                        date.setFill(Color.BLUE);
                        date.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                        rectangle.setStroke(Color.BLUE);
                    });
                    rectangle.setOnMouseExited(e -> {
                        date.setFill(Color.GREY);
                        date.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                        if (Colors.isDark) {
                            rectangle.setStroke(Color.rgb(74, 74, 74));
                        } else {
                            rectangle.setStroke(Color.rgb(230, 230, 230));
                        }
                    });
                }
                calendar.getChildren().add(stackPane);
                calendar.setAlignment(Pos.CENTER);
            }
        }
    }

    // Adding events box to the calendar
    boolean eventClicked = false;

    private void createCalendarevent(List<Event> calendarEvents, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);

        int eventCount = 0;
        for (Event event : calendarEvents) {
            if (event.getDateTime().getMonth().equals(dateFocus.getMonth()) && event.getDateTime().getYear() == dateFocus.getYear()) {
                eventCount++;
            }
            if (eventCount > 2) {
                // If more than 2 events, add "View more" text and break the loop
                Text viewMore = new Text("...");
                viewMore.setFont(Font.font("Arial", FontWeight.LIGHT, 10));

                mainBox.getChildren().add(viewMore);
                break;
            }

            VBox eventBox = new VBox();
            eventBox.setAlignment(Pos.CENTER);
            String clientName = event.getClient().getFirst_name();
            String clientText = clientName.length() > 8 ? clientName.substring(0, 7) + "..." : clientName;
            Text text = new Text(" " + clientText + ", " + event.getTime());
            text.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
            text.setWrappingWidth(rectangleWidth * 0.8);
            eventBox.getChildren().add(text);
            //change color based on what type is the event
            switch (event.getType()) {
                case 0:
                    eventBox.setStyle("-fx-border-color: GREY; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: LIGHTGREY; -fx-padding: 3;");
                    break;

                case 1:
                    eventBox.setStyle("-fx-border-color: #fc68a1; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: LIGHTPINK; -fx-padding: 3;");
                    break;

                case 2:
                    eventBox.setStyle("-fx-border-color: GREEN; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: LIGHTGREEN; -fx-padding: 3;");
                    break;

                default:
                    eventBox.setStyle("-fx-border-color: #fc68a1; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: LIGHTPINK; -fx-padding: 3;");
                    break;
            }
            if (event.getDateTime().getMonth().equals(dateFocus.getMonth()) && event.getDateTime().getYear() == dateFocus.getYear()) {
                mainBox.getChildren().add(eventBox);
            }
            text.setOnMouseClicked(mouseEvent -> {
                selectionList.getItems().clear();
                addEventList(event);
                selectedEvent = event;
                initButtonStyle();
                if (mouseEvent.getClickCount() == 2 && !mouseEvent.isConsumed()) {
                    mouseEvent.consume();
                    if (selectedEvent != null) {
                        if (selectedEvent.getInvoiceId() != 0) {
                            invoice();
                        } else {
                            edit();
                        }
                    }
                }
            });
            // Add hover effect
            switch (event.getType()) {
                case 0:
                    HoverController.addBorderHoverEffect(eventBox, "#ace3f2", "blue", 2, 10, "LIGHTGREY", "GREY", 1, 10);
                    break;

                case 1:
                    HoverController.addBorderHoverEffect(eventBox, "#ace3f2", "blue", 2, 10, "LIGHTPINK", "#fc68a1", 1, 10);
                    break;

                case 2:
                    HoverController.addBorderHoverEffect(eventBox, "#ace3f2", "blue", 2, 10, "LIGHTGREEN", "GREEN", 1, 10);
                    break;

                default:
                    HoverController.addBorderHoverEffect(eventBox, "#ace3f2", "blue", 2, 10, "LIGHTPINK", "#fc68a1", 1, 10);
                    break;
            }
        }

        mainBox.setTranslateY((rectangleHeight / 2) * 0.20);
        mainBox.setMaxWidth(rectangleWidth * 0.8);
        mainBox.setMaxHeight(rectangleHeight * 0.65);
        mainBox.setSpacing(1);
        stackPane.getChildren().add(mainBox);
    }

}