export interface ISaveSymptomDetailsSaveSpec {
    appointID: string,
    tabID: string,
    data: ISymptomsSaveSpec[]
 }

export interface ISymptomsSaveSpec {
    symptoms: string,
    duration : string,
    severity: string,
    note: string
}