import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getAvailableSlots } from "../../services/slotService";
import { bookAppointment } from "../../services/appointmentService";
import { useAuth } from "../../context/AuthContext";

const s = {
  page: { padding: "28px", maxWidth: "560px", margin: "0 auto" },
  heading: { fontSize: "20px", fontWeight: "700", color: "#1e293b", marginBottom: "6px" },
  sub: { fontSize: "13px", color: "#94a3b8", marginBottom: "24px" },
  card: { background: "#fff", padding: "24px", borderRadius: "8px", border: "1px solid #e2e8f0" },
  label: { display: "block", fontSize: "13px", color: "#475569", marginBottom: "5px", fontWeight: "500" },
  input: { width: "100%", padding: "9px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px", marginBottom: "16px", boxSizing: "border-box" },
  select: { width: "100%", padding: "9px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px", marginBottom: "16px", boxSizing: "border-box" },
  row: { display: "flex", gap: "10px" },
  btn: { flex: 1, padding: "10px", background: "#2563eb", color: "#fff", border: "none", borderRadius: "6px", fontSize: "14px", fontWeight: "600", cursor: "pointer" },
  loadBtn: { flex: 1, padding: "10px", background: "#64748b", color: "#fff", border: "none", borderRadius: "6px", fontSize: "14px", cursor: "pointer" },
  success: { background: "#f0fdf4", border: "1px solid #bbf7d0", color: "#16a34a", padding: "10px 14px", borderRadius: "6px", fontSize: "13px", marginTop: "14px" },
  error: { background: "#fef2f2", border: "1px solid #fecaca", color: "#dc2626", padding: "10px 14px", borderRadius: "6px", fontSize: "13px", marginTop: "14px" },
};

export default function BookAppointment() {
  const { doctorId } = useParams();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [date, setDate] = useState("");
  const [slots, setSlots] = useState([]);
  const [slotId, setSlotId] = useState("");
  const [mode, setMode] = useState("ONLINE");
  const [msg, setMsg] = useState({ text: "", type: "" });
  const [loading, setLoading] = useState(false);

  const loadSlots = async () => {
    if (!date) return;
    try {
      const res = await getAvailableSlots(doctorId, date);
      setSlots(res.data);
      if (res.data.length === 0) setMsg({ text: "No available slots for this date.", type: "error" });
      else setMsg({ text: "", type: "" });
    } catch {
      setMsg({ text: "Failed to load slots.", type: "error" });
    }
  };

  const handleBook = async () => {
    if (!slotId) return setMsg({ text: "Please select a slot.", type: "error" });
    setLoading(true);
    try {
      await bookAppointment({
        patientId: user.userId,
        doctorId: Number(doctorId),
        slotId: Number(slotId),
        mode,
      });
      setMsg({ text: "Appointment booked successfully!", type: "success" });
      setTimeout(() => navigate("/patient/appointments"), 1500);
    } catch (err) {
      setMsg({ text: err.response?.data?.message || "Booking failed.", type: "error" });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={s.page}>
      <div style={s.heading}>Book Appointment</div>
      <div style={s.sub}>Doctor #{doctorId}</div>
      <div style={s.card}>
        <label style={s.label}>Select Date</label>
        <input style={s.input} type="date" value={date} onChange={(e) => setDate(e.target.value)} />
        <button style={{ ...s.loadBtn, marginBottom: "16px", width: "100%" }} onClick={loadSlots}>
          Load Available Slots
        </button>

        {slots.length > 0 && (
          <>
            <label style={s.label}>Available Slots</label>
            <select style={s.select} value={slotId} onChange={(e) => setSlotId(e.target.value)}>
              <option value="">-- Select a slot --</option>
              {slots.map((sl) => (
                <option key={sl.id} value={sl.id}>
                  {sl.startTime} – {sl.endTime}
                </option>
              ))}
            </select>
          </>
        )}

        <label style={s.label}>Consultation Mode</label>
        <select style={s.select} value={mode} onChange={(e) => setMode(e.target.value)}>
          <option value="ONLINE">Online (Teleconsultation)</option>
          <option value="OFFLINE">Offline (In-clinic)</option>
        </select>

        <button style={s.btn} onClick={handleBook} disabled={loading}>
          {loading ? "Booking..." : "Confirm Booking"}
        </button>

        {msg.text && (
          <div style={msg.type === "success" ? s.success : s.error}>{msg.text}</div>
        )}
      </div>
    </div>
  );
}