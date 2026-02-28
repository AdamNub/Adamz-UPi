# 🏁 Adamz-UPi Library - Archived

This library has been archived because of **NPCI security changes** (February 2026).

## ❌ Why It Doesn't Work Anymore

Starting in 2026, NPCI introduced strict requirements that make custom UPI intent libraries impossible for individuals:

| Requirement | Problem |
|-------------|---------|
| Signed Intents | Requires PSP registration |
| Merchant Codes | Must match registered business VPA |
| Transaction IDs | Must be unique and verified |
| App Targeting | Apps block generic intents |

## ✅ What Does Still Work

- **QR Code generation** (the fallback in this library)
- **UPI intent** only with verified merchant accounts

## 📱 For Production Apps

Use these instead:
- [Razorpay](https://razorpay.com)
- [PayU](https://payu.in)
- [Cashfree](https://cashfree.com)
- [PhonePe for Business](https://business.phonepe.com)

## 🧪 For Testing

You can still test with:
- `success@upi` (may still work)
- QR code scanning (always works)

## 📜 License

MIT - use the QR code parts freely!

---

*Archived on February 28, 2026*
