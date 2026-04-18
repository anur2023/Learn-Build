import { useEffect, useState } from "react";
import { getPatientAppointments } from "../../services/appointmentService";
import { useAuth } from "../../context/AuthContext";

const statusStyle = (status) => {
  const map = {
    SCHEDULED: { bg: "#dbeafe", color: "#1d4ed8" },
    COMPLETED: { bg: "#dcfce7", color: "#16a34a" },
    CANCELLED: { bg: "#fee2e2", color: "#dc2626" },
    NO_SHOW: { bg: "#fef9c3", color: "#ca8a04" },
  };
  const style = map[status] || { bg: "#f1f5f9", color: "#64748b" };
  return { padding: "2px 10px", borderRadius: "12px", fontSize: "12px", fontWeight: "600", background: style.bg, color: style.color };
};

const s = {
  page: { padding: "28px", maxWidth: "960px", margin: "0 auto" },
  heading: { fontSize: "20px", fontWeight: "700", color: "#1e293b", marginBottom: "20px" },
  table: { width: "100%", borderCollapse: "collapse", background: "#fff", borderRadius: "8px", overflow: "hidden", border: "1px solid #e2e8f0" },
  th: { background: "#f8fafc", padding: "12px 16px", textAlign: "left", fontSize: "12px", fontWeight: "600", color: "#64748b", textTransform: "uppercase", borderBottom: "1px solid #e2e8f0" },
  td: { padding: "12px 16px", fontSize: "14px", color: "#334155", borderBottom: "1px solid #f1f5f9" },
  empty: { textAlign: "center", padding: "40px", color: "#94a3b8" },
};

export default function MyAppointments() {
  const [appointments, setAppointments] = useState([]);
  const { user } = useAuth();

  useEffect(() => {
    if (user?.userId) {
      getPatientAppointments(user.userId).then((res) => setAppointments(res.data));
    }
  }, [user]);

  return (
    <div style={s.page}>
      <div style={s.heading}>My Appointments</div>
      <table style={s.table}>
        <thead>
          <tr>
            <th style={s.th}>ID</th>
            <th style={s.th}>Doctor</th>
            <th style={s.th}>Mode</th>
            <th style={s.th}>Status</th>
            <th style={s.th}>Appointment Time</th>
            <th style={s.th}>Booked At</th>
          </tr>
        </thead>
        <tbody>
          {appointments.length === 0 ? (
            <tr><td colSpan={6} style={s.empty}>No appointments found</td></tr>
          ) : (
            appointments.map((a) => (
              <tr key={a.id}>
                <td style={s.td}>#{a.id}</td>
                <td style={s.td}>Dr. #{a.doctorId}</td>
                <td style={s.td}>{a.mode}</td>
                <td style={s.td}><span style={statusStyle(a.status)}>{a.status}</span></td>
                <td style={s.td}>{new Date(a.appointmentTime).toLocaleString()}</td>
                <td style={s.td}>{new Date(a.createdAt).toLocaleDateString()}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}