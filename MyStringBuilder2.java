// CS 0445 Spring 2019
// Read this class and its comments very carefully to make sure you implement
// the class properly.  The data and public methods in this class are identical
// to those MyStringBuilder, with the exception of the two additional methods
// shown at the end.  You cannot change the data or add any instance
// variables.  However, you may (and will need to) add some private methods.
// No iteration is allowed in this implementation. 

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class.  
public class MyStringBuilder2
{
 // These are the only three instance variables you are allowed to have.
 // See details of CNode class below.  In other words, you MAY NOT add
 // any additional instance variables to this class.  However, you may
 // use any method variables that you need within individual methods.
 // But remember that you may NOT use any variables of any other
 // linked list class or of the predefined StringBuilder or 
 // or StringBuffer class in any place in your code.  You may only use the
 // String class where it is an argument or return type in a method.
 private CNode firstC; // reference to front of list.  This reference is necessary
       // to keep track of the list
 private CNode lastC;  // reference to last node of list.  This reference is
       // necessary to improve the efficiency of the append()
       // method
 private int length;   // number of characters in the list

 // You may also add any additional private methods that you need to
 // help with your implementation of the public methods.

 // Create a new MyStringBuilder2 initialized with the chars in String s
public MyStringBuilder2(String s)
{
      if (s != null && s.length() > 0)
            makeBuilder(s, 0);
      else  // no String so initialize empty MyStringBuilder2
      {
            length = 0;
            firstC = null;
            lastC = null;
      }
}
// Recursive method to set up a new MyStringBuilder2 from a String
private void makeBuilder(String s, int pos)
{
      // Recursive case – we have not finished going through the String
     if (pos < s.length()-1){
                  // Note how this is done – we make the recursive call FIRST, then
            // add the node before it.  In this way the LAST node we add is
            // the front node, and it enables us to avoid having to make a
            // special test for the front node.  However, many of your
            // methods will proceed in the normal front to back way.
            makeBuilder(s, pos+1);
            firstC = new CNode(s.charAt(pos), firstC);
            length++;
      }
      else if (pos == s.length()-1) // Special case for last char in String
      {                             // This is needed since lastC must be
                                    // set to point to this node
            firstC = new CNode(s.charAt(pos));
            lastC = firstC;
            length = 1;
      }
      else  // This case should never be reached, due to the way the
            // constructor is set up.  However, I included it as a
      {     // safeguard (in case some other method calls this one)
            length = 0;
            firstC = null;
            lastC = null;
      }
}
 // Create a new MyStringBuilder2 initialized with the chars in array s
 public MyStringBuilder2(char [] s)
 {
      if (s != null && s.length > 0)
            arrBuilder(s, 0);
      else{
            length = 0;
            firstC = null;
            lastC = null;
      }
 }
 private void arrBuilder(char[] s, int pos){
      if(pos < s.length -1){//recursive case: in array, not yet at the end
           arrBuilder(s, pos+1);
           firstC = new CNode(s[pos], firstC); //voodoo magic line right here
           length++;
      }
      //base case: last char in array
      else if(pos == s.length -1){
           firstC = new CNode(s[pos]);
           lastC = firstC;
           length = 1;
      }
 }
 // Create a new empty MyStringBuilder2
 public MyStringBuilder2()
 {
      length = 0;
      firstC = null;
      lastC = null;
 }

 //Return a CNode at a specific index. 
 //NB: I wrote half my methods before realizing this would make things way easier,
 //I'll change them if i have time
 public CNode getNodeAt(int index, int pos, CNode curr) {
	 if(pos < index)
		 return getNodeAt(index, pos+1, curr.next);
	 return curr;
 }
 // Append MyStringBuilder2 b to the end of the current MyStringBuilder2, and
 // return the current MyStringBuilder2.  Be careful for special cases!
 public MyStringBuilder2 append(MyStringBuilder2 b)
 {
      if(b.length == 0) 
           return this;
      else
           return this.recAppendMSB(b.firstC);
 }
 private MyStringBuilder2 recAppendMSB(CNode curr){ //curr iterates thru b, newNode adds to this
      if(curr != null){
           CNode newNode = new CNode(curr.data);
           if(lastC != null) { //regular add to node
        	   this.lastC.next = newNode;//using "this" for clarity
        	   lastC = lastC.next;
           }else{//a case for creating the first node when adding to empty MSB2
        	   this.lastC = newNode;//create one node
        	   this.firstC = newNode;//and set both firstC and lastC to it. lastC reassigned later
           }
           length++;
           recAppendMSB(curr.next);//recursive call to add the next character
      }
      return this; //once we've called and added fully, return
 }

 // Append String s to the end of the current MyStringBuilder2, and return
 // the current MyStringBuilder2.  Be careful for special cases!
 public MyStringBuilder2 append(String s)
 {
	 if(s.length() == 0)
		 return this;
	 else
		 return this.recAppendS(0, s);
 }
 private MyStringBuilder2 recAppendS(int pos, String s){
	 if(pos < s.length()) {
		 CNode newNode = new CNode(s.charAt(pos));
		 if(lastC != null) {//if the object has nodes
			 this.lastC.next = newNode;
			 lastC = lastC.next;
		 }else {
			 this.lastC = newNode;
			 this.firstC = newNode;
		 }
		 length++;
		 recAppendS(pos + 1, s);
	 }
	 return this;
 }

 // Append char array c to the end of the current MyStringBuilder2, and
 // return the current MyStringBuilder2.  Be careful for special cases!
 public MyStringBuilder2 append(char [] c)
 {
	 if(c.length == 0) {
		 return this;
	 }else {
		 return this.recAppendArr(0, c);
	 }
 }
 private MyStringBuilder2 recAppendArr(int pos, char[] c) {
	 if(pos < c.length) {
		 CNode newNode = new CNode(c[pos]);
		 if(lastC != null) {
			 lastC.next = newNode;
			 lastC = lastC.next;
		 }else {
			 lastC = newNode;
			 firstC = newNode;
		 }
		 length++;
		 recAppendArr(pos + 1, c);
	 }
	 return this;
 }

 // Append char c to the end of the current MyStringBuilder2, and
 // return the current MyStringBuilder2.  Be careful for special cases!
 public MyStringBuilder2 append(char c) //I can't see any need for recursion here
 {
	 if(lastC != null) { //regular case
		 lastC.next = new CNode(c);
		 lastC = lastC.next;
		 length++;
	 }else {//case of empty MSB2
		 firstC = new CNode(c);
		 lastC = firstC;
		 length = 1;
	 }
	 return this;
 }

 // Return the character at location "index" in the current MyStringBuilder2.
 // If index is invalid, throw an IndexOutOfBoundsException.
 public char charAt(int index)
 {
	 if(index<0 || index >= length)
		 throw new IndexOutOfBoundsException();
	 else
		 return recCharAt(index, 0, firstC);
 }

 private char recCharAt(int index, int pos, CNode curr) {
	 if(pos==index)
		 return curr.data;
	 else
		 return recCharAt(index, pos+1, curr.next);
 }
 // Delete the characters from index "start" to index "end" - 1 in the
 // current MyStringBuilder2, and return the current MyStringBuilder2.
 // If "start" is invalid or "end" <= "start" do nothing (just return the
 // MyStringBuilder2 as is).  If "end" is past the end of the MyStringBuilder2, 
 // only remove up until the end of the MyStringBuilder2. Be careful for 
 // special cases!
 public MyStringBuilder2 delete(int start, int end)
 {
	 if(end > length)
		 end = length; //if end index is past the end of string, reset it
	 if(start >= length || end <= start)
		 return this;
	 else
		 return recDelete(start, end, firstC, firstC, 0, 0); 
 }
 private MyStringBuilder2 recDelete(int start, int end, CNode startNode, CNode endNode, int startPos, int endPos) {
	 if(endPos == end) { //if start and end nodes correct (start will always be right before end)
		 startNode.next = endNode; //set next, skipping middle
		 int nodesRemoved = end - start;
		 length -= nodesRemoved; //adjust length
		 if(start == 0)//reassign start node if we delete it
			 firstC = endNode;
		 return this;
	 }
	 if(endPos < end) { //advance to end node
		 endNode = endNode.next;
		 endPos++;
	 }
	 if(startPos < start - 1) {
		 startNode = startNode.next;
		 startPos++;
	 }
	 return recDelete(start, end, startNode, endNode, startPos, endPos);
 }

 // Delete the character at location "index" from the current
 // MyStringBuilder2 and return the current MyStringBuilder2.  If "index" is
 // invalid, do nothing (just return the MyStringBuilder2 as is).
 // Be careful for special cases!
 public MyStringBuilder2 deleteCharAt(int index)
 {
	 if(index >= length || index < 0)
		 return this;
	 else
		 return recDeleteCharAt(index, 0, firstC);
 }
 private MyStringBuilder2 recDeleteCharAt(int index, int pos, CNode curr) {
	 if(index == 0){ //if we're removing the first node, we simply declare the next node as the first
         firstC = firstC.next;
         length--;
         return this;
	 }else if(pos < index - 1) {
		 return recDeleteCharAt(index, pos+1, curr.next);
	 }else {
		 curr.next = curr.next.next; //reassign next node pointer
		 length--;
		 return this;
	 }	 
 }

 // Find and return the index within the current MyStringBuilder2 where
 // String str first matches a sequence of characters within the current
 // MyStringBuilder2.  If str does not match any sequence of characters
 // within the current MyStringBuilder2, return -1.  Think carefully about
 // what you need to do for this method before implementing it.
 public int indexOf(String str){
     return recIndexOf(str, 0, firstC, firstC, 0);
}
private int recIndexOf(String str, int pos, CNode head, CNode looper, int strPos) {
	 if(strPos == str.length())//if we've gone all thru the string and it matches
		 return pos;
	 else if(strPos == str.length()-1 && looper.next == null)//special success: str and LL end on same char
		 return pos;
	 else if(pos == length) //failure case
		 return (-1);
	 //case: char matches, advance in string
	 if(looper.data == str.charAt(strPos) && looper.next != null) {
		 return recIndexOf(str, pos, head, looper.next, strPos+1);
	 } else //case: char does not match, advance in LL
	 	 return recIndexOf(str, pos+1, head.next, head.next, 0);
}


 // Insert String str into the current MyStringBuilder2 starting at index
 // "offset" and return the current MyStringBuilder2.  if "offset" == 
 // length, this is the same as append.  If "offset" is invalid
 // do nothing.
//TODO SPECIAL CASE INSERT AT 0
 public MyStringBuilder2 insert(int offset, String str)
 {
	 if(offset == length) { //insert at end
		 this.append(str);
		 return this;
	 }else if(offset < 0 || offset > length) //invalid offset
		 return this;
	 else if(offset == 0) { //special case insert at beginning. this does the first node then calls 
		 CNode newNode = new CNode(str.charAt(0));
		 newNode.next = firstC;
		 firstC = newNode;
		 return recStrInsert(firstC.next, firstC, str, 1); //calls at position 1 b/c we added 0 already
	 }
	 else { //regular case
		 CNode head = getNodeAt(offset-1, 0, firstC);
		 return recStrInsert(head.next, head, str, 0);
	 }
 }
private MyStringBuilder2 recStrInsert(CNode after, CNode curr, String str, int strPos) {
	if(strPos < str.length()) {
		curr.next = new CNode(str.charAt(strPos));
		return recStrInsert(after, curr.next, str, strPos+1);
	}
	else if(strPos == str.length()) {
		curr.next = after;
		length += str.length();
	}
	return this;
}
 // Insert character c into the current MyStringBuilder2 at index
 // "offset" and return the current MyStringBuilder2.  If "offset" ==
 // length, this is the same as append.  If "offset" is invalid, 
 // do nothing.
 public MyStringBuilder2 insert(int offset, char c)
 {
	 if(offset == length) {
		 this.append(c);
		 return this;
	 }else if(offset < 0 || offset > length)
		 return this;
	 else {
		 CNode before = getNodeAt(offset-1, 0, firstC);
		 CNode after = before.next;
		 CNode newNode = new CNode(c);
		 before.next = newNode;
		 newNode.next = after;
		 length++;		 
	 }
	 return this;
 }

 // Insert char array c into the current MyStringBuilder2 starting at index
 // index "offset" and return the current MyStringBuilder2.  If "offset" is
 // invalid, do nothing.
 public MyStringBuilder2 insert(int offset, char [] c)
 {
	 if(offset == length) {
		 this.append(c);
		 return this;
	 }else if(offset < 0 || offset > length) //invalid offset
		 return this;
	 else if(offset == 0) { //special case insert at beginning. this does the first node then calls 
		 CNode newNode = new CNode(c[0]);
		 newNode.next = firstC;
		 firstC = newNode;
		 return recArrInsert(firstC.next, firstC, c, 1); //calls at position 1 b/c we added 0 already
	 }
	 else { //default case
		 CNode head = getNodeAt(offset-1, 0, firstC);
		 return recArrInsert(head.next, head, c, 0);
	 }
 }
 private MyStringBuilder2 recArrInsert(CNode after, CNode curr, char[] c, int arrPos) {
		if(arrPos < c.length) {
			curr.next = new CNode(c[arrPos]);
			return recArrInsert(after, curr.next, c, arrPos+1);
		}
		else if(arrPos == c.length) {
			curr.next = after;
			length += c.length;
		}
		return this;
	}
 
 // Return the length of the current MyStringBuilder2
 public int length()
 {
	 return length;
 }

 // Delete the substring from "start" to "end" - 1 in the current
 // MyStringBuilder2, then insert String "str" into the current
 // MyStringBuilder2 starting at index "start", then return the current
 // MyStringBuilder2.  If "start" is invalid or "end" <= "start", do nothing.
 // If "end" is past the end of the MyStringBuilder2, only delete until the
 // end of the MyStringBuilder2, then insert.  This method should be done
 // as efficiently as possible.  In particular, you may NOT simply call
 // the delete() method followed by the insert() method, since that will
 // require an extra traversal of the linked list.
 public MyStringBuilder2 replace(int start, int end, String str)
 {
	 if(start < 0 || start > length || end <= start) //special case: invalid index
		 return this;
	 else if(end >= length){ //special case: replaces past end...
		 lastC = getNodeAt(start-1, 0, firstC);
		 length = start;
		 this.append(str);
		 return this;
	 }else{
		 length = length + str.length() - (end - start) + 1;
		 return recStrReplace(getNodeAt(start-1, 0, firstC), getNodeAt(end, 0, firstC), firstC, str, 0);
	 }
 }
 private MyStringBuilder2 recStrReplace(CNode start, CNode end, CNode curr, String str, int strPos) {
	 if(strPos == 0){ //special case first node add
		 CNode newNode = new CNode(str.charAt(0));
		 start.next = newNode;
		 return recStrReplace(start, end, newNode, str, 1);
	 }
	 else if(strPos < str.length()-1) { //regular add along the string
		 CNode newNode = new CNode(str.charAt(strPos));
		 curr.next = newNode;
		 return recStrReplace(start, end, curr.next, str, strPos+1);
	 }else { //end case
		 CNode newNode = new CNode(str.charAt(strPos));
		 curr.next = newNode;
		 newNode.next = end; //TODO RESET LASTC
	 }
	 return this;				 		
 }

 // Reverse the characters in the current MyStringBuilder2 and then
 // return the current MyStringBuilder2.
 public MyStringBuilder2 reverse()
 {
	 CNode newNode = new CNode(firstC.data);
	 lastC = newNode;
	 return recReverse(firstC.next, newNode);
 }
 private MyStringBuilder2 recReverse(CNode oldNode, CNode newNextNode) {
	if(oldNode.next != null) { 
	 	CNode newNode = new CNode(oldNode.data);
	 	newNode.next = newNextNode;
	 	return recReverse(oldNode.next, newNode);
	}else{
		CNode newNode = new CNode(oldNode.data);
		newNode.next = newNextNode;
		firstC = newNode;
		return this;
	}
 }
 
 // Return as a String the substring of characters from index "start" to
 // index "end" - 1 within the current MyStringBuilder2
 public String substring(int start, int end)
 {
	 int strLength = end - start;
	 char[] retval = new char[strLength];
	 return recSubstring(getNodeAt(start, 0, firstC), strLength, 0, retval);
 }
 private String recSubstring(CNode curr, int strLength, int pos, char[] retval) {
	 if(pos < strLength) { //regular add case
		 retval[pos] = curr.data;
		 return recSubstring(curr.next, strLength, pos+1, retval);
	 }
	 String retString = new String(retval);
     return retString;
 }
 
 // Return the entire contents of the current MyStringBuilder2 as a String
 public String toString()
 {
      char [] c = new char[length];
      //System.out.println("creating array of length " + length);
      getString(c, 0, firstC);
      return (new String(c));
 }
 private void getString(char[] c, int pos, CNode curr){
      if(curr != null){
           c[pos] = curr.data;
           getString(c, pos+1, curr.next);
      }
 }


 // Find and return the index within the current MyStringBuilder2 where
 // String str LAST matches a sequence of characters within the current
 // MyStringBuilder2.  If str does not match any sequence of characters
 // within the current MyStringBuilder2, return -1.  Think carefully about
 // what you need to do for this method before implementing it.  For some
 // help with this see the Assignment 3 specifications.
 public int lastIndexOf(String str)
 {
	 return recLastIndexOf(str, 0, firstC, firstC, 0, -1);
 }
 //Cases could be in more intuitive order my b. i just adapted this from indexOf
 private int recLastIndexOf(String str, int pos, CNode head, CNode looper, int strPos, int lastValidPos) {
	 if(head.next == null) { //if we've reached the end of the LL
		 return lastValidPos;
	 }else if(strPos == str.length()) {//if we've gone all thru the string and it matches, we advance to next node
 		 lastValidPos = pos;
 		 return recLastIndexOf(str, pos+1, head.next, head.next, 0, lastValidPos);
 	 }
 	 else if(strPos == str.length()-1 && looper.next == null) {//special success: str and LL end on same char
 		 lastValidPos = pos;
 		 return recLastIndexOf(str, pos+1, head.next, head.next, 0, lastValidPos);
 	 }
 	 //case: char matches, advance in string
 	 if(looper.data == str.charAt(strPos) && looper.next != null) {
 		 return recLastIndexOf(str, pos, head, looper.next, strPos+1, lastValidPos);
 	 } else //case: char does not match, advance in LL
 	 	 return recLastIndexOf(str, pos+1, head.next, head.next, 0, lastValidPos);
 }
 
 // Find and return an array of MyStringBuilder2, where each MyStringBuilder2
 // in the return array corresponds to a part of the match of the array of
 // patterns in the argument.  If the overall match does not succeed, return
 // null.  For much more detail on the requirements of this method, see the
 // Assignment 3 specifications.
 
 //Note to reader: couldn't get this to work w/backtracking. thought it was smart to base cases off the pdf, but...
 //tbh i feel like this would be easier with a well placed loop or two
 public MyStringBuilder2 [] regMatch(String [] pats)
 {
	  MyStringBuilder2 [] answers = new MyStringBuilder2[pats.length];
      return recRegMatch(pats, 0, 0, fill(answers, 0), 1, 0);
 }
 private MyStringBuilder2[] recRegMatch(String [] pats, int listPos, int arrPos, MyStringBuilder2[] answers, int state, int overallPos) {
	 //turn current char in list into string so that we can use the contains method
	 char[] temp = new char[1];
	 temp[0] = getNodeAt(listPos, 0, firstC).data;
	 String listChar = new String(temp);
	 //well that took longer than it should, hopefully contains() saves us time....
	 

	 
	 //STATE 1: No Characters in pattern 0 matched
	 if(state == 1) {
		 if(listPos == length-1)  //There are no characters left in the MyStringBuilder2. FAIL
			 return fill(answers, 0);
		 else if(pats[0].contains(listChar)) { //the current character DOES match pattern 0
			 answers[0].append(listChar);
			 return recRegMatch(pats, listPos+1, 0, answers, 2, overallPos);
		 }
	     else //the character does NOT match pattern 0
			 return recRegMatch(pats, listPos+1, 0, answers, 1, overallPos);		 
	 }
	 else if(state == 2) { //STATE 2: At least one char in pattern i matched
		 //If the character MATCHES pattern i, stay in this state and move down to the next character in the MyStringBuilder2.
		 if(pats[arrPos].contains(listChar) && listPos < length-1) {
			 answers[arrPos].append(listChar);
			 return recRegMatch(pats, listPos+1, arrPos, answers, 2, overallPos);
		 }else if(pats[arrPos].contains(listChar) && listPos == length-1 && arrPos == pats.length-1) {
			 answers[arrPos].append(listChar);
			 return answers;
		 }
		 //If the character DOES NOT match pattern i and i is the LAST pattern, return TRUE(success)
		 if(!pats[arrPos].contains(listChar) && arrPos == pats.length-1)
			 return answers;
		 //If the character DOES NOT match pattern i and i is NOT the LAST pattern, move to pattern i+1 and State 3) but stay at the SAME character in the MyStringBuilder2
		 if(!pats[arrPos].contains(listChar) && arrPos < pats.length-1)
			 return recRegMatch(pats, listPos, arrPos+1, answers, 3, overallPos);
	 }else if(state == 3) { //STATE 3: no chars in pattern i match 
		 //if the character MATCHES pattern i, move to State 2) and move down to the next character in the MyStringBuilder2
		 if(pats[arrPos].contains(listChar)) {
			 answers[arrPos].append(listChar);
			 return recRegMatch(pats, listPos+1, arrPos, answers, 2, overallPos);
		 }
		 else { //If the character DOES NOT match pattern i return FALSE–the algorithm must backtrack
			 overallPos++;
			 return recRegMatch(pats, overallPos, 0, fill(answers, 0), 1, overallPos);
		 }
	 }
	 
	 return null;
 }
 //This is a method to fill the MSB2 array with empty MSB2 objects. I could've used a for loop and did this in two lines. j u s t
 private MyStringBuilder2[] fill(MyStringBuilder2[] arr, int index) {
	 if(index < arr.length) {
		 arr[index] = new MyStringBuilder2();
		 return fill(arr, index+1);
	 }else {
		 return arr;
	 }
 }
 
 
 // You must use this inner class exactly as specified below.  Note that
 // since it is an inner class, the MyStringBuilder2 class MAY access the
 // data and next fields directly.
 private class CNode
 {
  private char data;
  private CNode next;

  public CNode(char c)
  {
   data = c;
   next = null;
  }

  public CNode(char c, CNode n)
  {
   data = c;
   next = n;
  }
 }
}

