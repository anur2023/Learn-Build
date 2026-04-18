import { useEffect, useState } from "react";
import { getAllAppointments, updateAppointmentStatus } from "../../services/appointmentService";

const s = {
  page: { padding: "28px", maxWidth: "1100px", margin: "0 auto" },
  heading: { fontSize: "20px", fontWeight: "700", color: "#1e293b", marginBottom: "20px" },
  table: { width: "100%", borderCollapse: "collapse", background: "#fff", borderRadius: "8px", border: "1px solid #e2e8f0" },
  th: { background: "#f8fafc", padding: "11px 16px", textAlign: "left", fontSize: "12px", fontWeight: "600", color: "#64748b", textTransform: "uppercase", borderBottom: "1px solid #e2e8f0" },
  td: { padding: "11px 16px", fontSize: "14px", color: "#334155", borderBottom: "1px solid #f1f5f9" },
  select: { padding: "6px 10px", border: "1px solid #cbd5e1", borderRadius: "5px", fontSize: "13px" },
};

export default function AllAppointments() {
  const [appointments, setAppointments] = useState([]);

  const fetchAll = () => getAllAppointments().then((res) => setAppointments(res.data));
  useEffect(() => { fetchAll(); }, []);

  const handleStatusChange = async (id, status) => {
    await updateAppointmentStatus(id, status);
    fetchAll();
  };

  return (
    <div style={s.page}>
      <div style={s.heading}>All Appointments</div>
      <table style={s.table}>
        <thead>
          <tr>
            <th style={s.th}>ID</th>
            <th style={s.th}>Patient</th>
            <th style={s.th}>Doctor</th>
            <th style={s.th}>Slot</th>
            <th style={s.th}>Mode</th>
            <th style={s.th}>Status</th>
            <th style={s.th}>Time</th>
            <th style={s.th}>Update</th>
          </tr>
        </thead>
        <tbody>
          {appointments.map((a) => (
            <tr key={a.id}>
              <td style={s.td}>#{a.id}</td>
              <td style={s.td}>#{a.patientId}</td>
              <td style={s.td}>#{a.doctorId}</td>
              <td style={s.td}>#{a.slotId}</td>
              <td style={s.td}>{a.mode}</td>
              <td style={s.td}>{a.status}</td>
              <td style={s.td}>{new Date(a.appointmentTime).toLocaleString()}</td>
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