`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/15 10:27:41
// Design Name: 
// Module Name: led
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module led#(parameter size=8)(
input clk,
input [size-1:0]consult,
input [size-1:0]remainder,
output[3:0]wei_show,
output[7:0]duan_show 
    );
    //·ÖÆµ
    integer cnt;
    reg clk_400hz;
    always@(posedge clk)begin
        if(cnt==32'd100000)begin
            cnt<=0;
            clk_400hz=~clk_400hz;
        end
        else begin
            cnt<=cnt+1'b1;
        end
    end
    
    reg[3:0]wei_ctrl=4'b1110;
    always@(posedge clk_400hz)begin
        wei_ctrl<={wei_ctrl[2:0],wei_ctrl[3]};
    end
    
    reg [3:0]duan_ctrl;
    always@(wei_ctrl)begin
        case(wei_ctrl)
        4'b1110: duan_ctrl = consult[3:0];
        4'b1101: duan_ctrl = consult[7:4];
        4'b1011: duan_ctrl = remainder[3:0];
        4'b0111: duan_ctrl = remainder[7:4];
        default: duan_ctrl = 4'hf;
        endcase
    end
    
    reg[7:0]duan;
    always@(duan_ctrl)begin
        case(duan_ctrl)
        4'h0:duan=8'b1100_0000; //h-a
        4'h1:duan=8'b1111_1001;
        4'h2:duan=8'b1010_0100;
        4'h3:duan=8'b1011_0000;
        4'h4:duan=8'b1001_1001;
        4'h5:duan=8'b1001_0010;
        4'h6:duan=8'b1000_0010;
        4'h7:duan=8'b1111_1000;
        4'h8:duan=8'b1000_0000;
        4'h9:duan=8'b1001_0000;
        4'ha:duan=8'b1000_1000;
        4'hb:duan=8'b1000_0011;
        4'hc:duan=8'b1100_0110;
        4'hd:duan=8'b1010_0001;
        4'he:duan=8'b1000_0110;
        4'hf:duan=8'b1000_1110;
        default:duan=8'b1100_0000;
        endcase
    end
    
    assign wei_show = wei_ctrl;
    assign duan_show = duan;

endmodule
