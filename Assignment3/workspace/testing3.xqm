
import schema namespace p="http://www.example.org/schemas/clinic/patient" at "Patient.xsd";
import schema namespace t="http://www.example.org/schemas/clinic/Treatment" at "Treament.xsd";
import schema namespace c="http://www.example.org/clinic" at "Clinic.xsd";
declare namespace ti="http://www.example.org/schemas/clinic/treatmentInfo";
declare function local:getTreatmentInfo($database as element(c:Clinic))
as item()*
{
    for $patient in $database/p:patients
    
    for $treatment in $patient/p:treatments/p:treatment/node()
    return 
    typeswitch($treatment)
    case $Drug as element(t:DrugTreatment)
    return<ti:treatmentInfo>
            <ti:name>DrugTreatment</ti:name>
            <ti:pid>{$patient/p:PatientId/text()}</ti:pid>
            <ti:ProviderId>{$treatment/../t:ProviderId/text()}</ti:ProviderId>
        </ti:treatmentInfo>
        
    case $Surgery as element(t:Surgery)
    return<ti:treatmentInfo>
            <ti:name>Surgery</ti:name>
            <ti:pid>{$patient/p:PatientId/text()}</ti:pid>
            <ti:ProviderId>{$treatment/../t:ProviderId/text()}</ti:ProviderId>
        </ti:treatmentInfo>
    case $Radiology as element(t:Radiology)
    return<ti:treatmentInfo>
            <ti:name>Radiology</ti:name>
            <ti:pid>{$patient/p:PatientId/text()}</ti:pid>
            <ti:ProviderId>{$treatment/../t:ProviderId/text()}</ti:ProviderId>
        </ti:treatmentInfo>
    default
    return ""
};

let $clinic :=doc("ClinicData.xml")/c:Clinic
 return <t:drug>{local:getTreatmentInfo($clinic)}</t:drug>