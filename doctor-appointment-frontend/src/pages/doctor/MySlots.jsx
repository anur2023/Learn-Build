import { useEffect, useState } from "react";
import { getAllSlotsByDoctor, createSlot, deleteSlot } from "../../services/slotService";

const s = {
  page: { padding: "28px", maxWidth: "960px", margin: "0 auto" },
  heading: { fontSize: "20px", fontWeight: "700", color: "#1e293b", marginBottom: "20px" },
  card: { background: "#fff", padding: "20px", borderRadius: "8px", border: "1px solid #e2e8f0", marginBottom: "24px" },
  cardTitle: { fontSize: "15px", fontWeight: "600", color: "#334155", marginBottom: "16px" },
  row: { display: "flex", gap: "12px", flexWrap: "wrap", alignItems: "flex-end" },
  group: { display: "flex", flexDirection: "column" },
  label: { fontSize: "12px", color: "#64748b", marginBottom: "4px", fontWeight: "500" },
  input: { padding: "8px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px" },
  addBtn: { padding: "8px 18px", background: "#2563eb", color: "#fff", border: "none", borderRadius: "6px", fontSize: "14px", cursor: "pointer", fontWeight: "500" },
  table: { width: "100%", borderCollapse: "collapse", background: "#fff", borderRadius: "8px", border: "1px solid #e2e8f0" },
  th: { background: "#f8fafc", padding: "11px 16px", textAlign: "left", fontSize: "12px", fontWeight: "600", color: "#64748b", textTransform: "uppercase", borderBottom: "1px solid #e2e8f0" },
  td: { padding: "11px 16px", fontSize: "14px", color: "#334155", borderBottom: "1px solid #f1f5f9" },
  delBtn: { padding: "5px 12px", background: "#ef4444", color: "#fff", border: "none", borderRadius: "5px", fontSize: "12px", cursor: "pointer" },
  msg: { fontSize: "13px", marginTop: "10px" },
};

export default function MySlots() {
  const [slots, setSlots] = useState([]);
  const [doctorId, setDoctorId] = useState(localStorage.getItem("doctorId") || "");
  const [form, setForm] = useState({ date: "", startTime: "", endTime: "" });
  const [msg, setMsg] = useState({ text: "", ok: true });

  const loadSlots = () => {
    if (!doctorId) return;
    getAllSlotsByDoctor(doctorId).then((res) => setSlots(res.data)).catch(() => setSlots([]));
  };

  useEffect(() => { loadSlots(); }, []);

  const handleAdd = async () => {
    if (!doctorId) return setMsg({ text: "Enter your Doctor Profile ID first.", ok: false });
    try {
      await createSlot({ doctorId: Number(doctorId), ...form });
      setMsg({ text: "Slot created!", ok: true });
      loadSlots();
    } catch (err) {
      setMsg({ text: err.response?.data?.message || "Failed to create slot.", ok: false });
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteSlot(id);
      setSlots(slots.filter((s) => s.id !== id));
    } catch (err) {
      alert(err.response?.data?.message || "Cannot delete slot.");
    }
  };

  return (
    <div style={s.page}>
      <div style={s.heading}>Manage My Slots</div>
      <div style={s.card}>
        <div style={s.cardTitle}>Create New Slot</div>
        <div style={s.row}>
          <div style={s.group}>
            <label style={s.label}>Doctor Profile ID</label>
            <input style={s.input} value={doctorId} placeholder="e.g. 1"
              onChange={(e) => { setDoctorId(e.target.value); localStorage.setItem("doctorId", e.target.value); }} />
          </div>
          <div style={s.group}>
            <label style={s.label}>Date</label>
            <input style={s.input} type="date" value={form.date}
              onChange={(e) => setForm({ ...form, date: e.target.value })} />
          </div>
          <div style={s.group}>
            <label style={s.label}>Start Time</label>
            <input style={s.input} type="time" value={form.startTime}
              onChange={(e) => setForm({ ...form, startTime: e.target.value })} />
          </div>
          <div style={s.group}>
            <label style={s.label}>End Time</label>
            <input style={s.input} type="time" value={form.endTime}
              onChange={(e) => setForm({ ...form, endTime: e.target.value })} />
          </div>
          <button style={s.addBtn} onClick={handleAdd}>Add Slot</button>
          <button style={{ ...s.addBtn, background: "#64748b" }} onClick={loadSlots}>Refresh</button>
        </div>
        {msg.text && <div style={{ ...s.msg, color: msg.ok ? "#16a34a" : "#dc2626" }}>{msg.text}</div>}
      </div>

      <table style={s.table}>
        <thead>
          <tr>
            <th style={s.th}>ID</th>
            <th style={s.th}>Date</th>
            <th style={s.th}>Start Time</th>
            <th style={s.th}>End Time</th>
            <th style={s.th}>Booked</th>
            <th style={s.th}>Action</th>
          </tr>
        </thead>
        <tbody>
          {slots.map((sl) => (
            <tr key={sl.id}>
              <td style={s.td}>#{sl.id}</td>
              <td style={s.td}>{sl.date}</td>
              <td style={s.td}>{sl.startTime}</td>
              <td style={s.td}>{sl.endTime}</td>
              <td style={s.td}>{sl.booked ? "✅ Yes" : "⬜ No"}</td>
              <td style={s.td}>
                {!sl.booked && <button style={s.delBtn} onClick={() => handleDelete(sl.id)}>Delete</button>}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}