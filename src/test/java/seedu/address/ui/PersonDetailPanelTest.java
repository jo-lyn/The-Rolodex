package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.ui.PersonDetailPanel.PERSON_ADDRESS_ICON;
import static seedu.address.ui.PersonDetailPanel.PERSON_EMAIL_ICON;
import static seedu.address.ui.PersonDetailPanel.PERSON_PHONE_ICON;

import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonDetailPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class PersonDetailPanelTest extends GuiUnitTest {

    private PersonDetailPanel personDetailPanel;
    private PersonDetailPanelHandle personDetailPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> personDetailPanel = new PersonDetailPanel());
        uiPartRule.setUiPart(personDetailPanel);

        personDetailPanelHandle = new PersonDetailPanelHandle(getChildNode(personDetailPanel.getRoot(),
                PersonDetailPanelHandle.PERSON_DETAIL_ID));
    }

    @Test
    public void display() {
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPanelDisplaysPerson(ALICE, personDetailPanelHandle);

        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertPanelDisplaysPerson(BOB, personDetailPanelHandle);
    }

    /**
     * Asserts that {@code actualPerson} displays the details of {@code expectedPerson}.
     */
    private void assertPanelDisplaysPerson(ReadOnlyPerson expectedPerson, PersonDetailPanelHandle panel) {
        guiRobot.pauseForHuman();

        assertEquals(expectedPerson.getName().toString(), panel.getName());
        assertEquals(PERSON_PHONE_ICON + expectedPerson.getPhone().toString(), panel.getPhone());
        assertEquals(PERSON_ADDRESS_ICON + expectedPerson.getAddress().toString(), panel.getAddress());
        assertEquals(PERSON_EMAIL_ICON + expectedPerson.getEmail().toString(), panel.getEmail());
        assertEquals(expectedPerson.getRemark().toString(), panel.getRemark());

        panel.updateTags();
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                panel.getTags());
    }
}