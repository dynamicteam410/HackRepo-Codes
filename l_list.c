// function to delete first node: uses approach 2.3
// See http://ideone.com/9GwTb for complete program and output
void deleteFirst(struct Node **head_ref)
{
	if(*head_ref != NULL)
	{
	// store the old value of pointer to head pointer
	struct Node *temp = *head_ref;

	// Change head pointer to point to next node
	*head_ref = (*head_ref)->next;

	// delete memory allocated for the previous head node
	free(temp);
	}
}
