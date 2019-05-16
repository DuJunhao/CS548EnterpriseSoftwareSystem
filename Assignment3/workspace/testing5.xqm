
import schema namespace p="http://www.example.org/schemas/clinic/patient" at "Patient.xsd";
import schema namespace t="http://www.example.org/schemas/clinic/Treatment" at "Treament.xsd";
import schema namespace c="http://www.example.org/clinic" at "Clinic.xsd";
declare namespace di="http://www.example.org/schemas/clinic/drugInfo";
declare function local:getDrugInfo($database as element(c:Clinic))
as item()*
{
    for $patient in $database/p:patients
    for $treatment in $patient/p:treatments/p:treatment
    where $treatment//t:DrugTreatment
    return
        <di:drugInfo>
            <di:patients>{$treatment/../../p:PatientId/text()}</di:patients>
            <di:name>{$treatment/t:DrugTreatment/t:name/text()}</di:name>
            <di:diagnosis>{$treatment/t:diagnosis/text()}</di:diagnosis>
            <di:dosage>{$treatment/t:DrugTreatment/t:dosage/text()}</di:dosage>
            <di:physician>{$treatment/t:DrugTreatment/t:physician/text()}</di:physician>
        </di:drugInfo>
};

let $clinic :=doc("ClinicData.xml")/c:Clinic
 return <di:drug>{local:getDrugInfo($clinic)}</di:drug>