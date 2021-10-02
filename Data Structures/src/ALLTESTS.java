import org.junit.runner.RunWith;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
	SinglyLinkedListTest.class,
	DoublyLinkedListTest.class,
	
	LinkedQueueTest.class,
	LinkedBinaryTreeTest.class,
	TreeMapTest.class,
	AVLTreeMapTest.class,
	SplayTreeMapTest.class
})

public class ALLTESTS {

}
