export default interface ITask {
    id: number,
    title: String,
    creator: any,
    description: String,
    approvedParticipants: number,
    numberOfParticipants: number,
    priority: any,
    category: any,
    endDate: string,
    photos: String[],
    creationDate: String
}
