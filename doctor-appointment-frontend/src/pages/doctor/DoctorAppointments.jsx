import { useEffect, useState } from "react";
import { getDoctorAppointments, updateAppointmentStatus } from "../../services/appointmentService";

const s = {
  page: { padding: "28px", maxWidth: "960px", margin: "0 auto" },
  heading: { fontSize: "20px", fontWeight: "700", color: "#1e293b", marginBottom: "20px" },
  searchBar: { display: "flex", gap: "10px", marginBottom: "20px" },
  input: { padding: "8px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px", width: "200px" },
  btn: { padding: "8px 16px", background: "#2563eb", color: "#fff", border: "none", borderRadius: "6px", fontSize: "14px", cursor: "pointer" },
  table: { width: "100%", borderCollapse: "collapse", background: "#fff", borderRadius: "8px", border: "1px solid #e2e8f0" },
  th: { background: "#f8fafc", padding: "11px 16px", textAlign: "left", fontSize: "12px", fontWeight: "600", color: "#64748b", textTransform: "uppercase", borderBottom: "1px solid #e2e8f0" },
  td: { padding: "11px 16px", fontSize: "14px", color: "#334155", borderBottom: "1px solid #f1f5f9" },
  select: { padding: "6px 10px", border: "1px solid #cbd5e1", borderRadius: "5px", fontSize: "13px" },
};

export default function DoctorAppointments() {
  const [appointments, setAppointments] = useState([]);
  const [doctorId, setDoctorId] = useState(localStorage.getItem("doctorId") || "");

  const fetchAppointments = () => {
    if (!doctorId) return;
    getDoctorAppointments(doctorId).then((res) => setAppointments(res.data));
  };

  useEffect(() => { fetchAppointments(); }, []);

  const handleStatusChange = async (id, status) => {
    await updateAppointmentStatus(id, status);
    fetchAppointments();
  };

  return (
    <div style={s.page}>
      <div style={s.heading}>Patient Appointments</div>
      <div style={s.searchBar}>
        <input style={s.input} placeholder="Doctor Profile ID" value={doctorId}
          onChange={(e) => { setDoctorId(e.target.value); localStorage.setItem("doctorId", e.target.value); }} />
        <button style={s.btn} onClick={fetchAppointments}>Load</button>
      </div>
      <table style={s.table}>
        <thead>
          <tr>
            <th style={s.th}>ID</th>
            <th style={s.th}>Patient ID</th>
            <th style={s.th}>Mode</th>
            <th style={s.th}>Current Status</th>
            <th style={s.th}>Update Status</th>
          </tr>
        </thead>
        <tbody>
          {appointments.map((a) => (
            <tr key={a.id}>
              <td style={s.td}>#{a.id}</td>
              <td style={s.td}>Patient #{a.patientId}</td>
              <td style={s.td}>{a.mode}</td>
              <td style={s.td}>{a.status}</td>
              <td style={s.td}>
                <select style={s.select} value={a.status}
                  onChange={(e) => handleStatusChange(a.id, e.target.value)}>
                  <option value="SCHEDULED">SCHEDULED</option>
                  <option value="COMPLETED">COMPLETED</option>
                  <option value="CANCELLED">CANCELLED</option>
                  <option value="NO_SHOW">NO_SHOW</option>
                </select>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}